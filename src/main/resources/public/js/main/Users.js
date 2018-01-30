Ext.define('Users', {
    extend: 'Ext.grid.Panel',
    initComponent: function() {
        this.title = 'Пользователи';
        this.border = true;
        this.frame = false;
        this.region = 'center';
        this.loadMask = true;
        this.emptyText  = 'Нет данных';
        this.margins = '2 2 2 2';
        this.id = 'UsersGrid';
        this.queryParams = {};
        
        this.initData();
        this.initColumns();
        

        Users.superclass.initComponent.apply(this, arguments);
    },
    initColumns: function() {
      this.columns = [
          {header: 'ID', align: 'left', width: 70, dataIndex: 'id' },
          {header: 'Тип пользователя', align: 'left', width: 110, dataIndex: 'user_type' },
          {header: 'Login', align: 'left', width: 100, dataIndex: 'login' },
          {header: 'Статус', align: 'left', width: 110, dataIndex: 'activ_flag', renderer: this.statusRenderer },
          {header: 'Дата последнего визита', align: 'left', width: 200, dataIndex: 'last_visit', renderer: Ext.util.Format.dateRenderer('d.m.Y H:i:s') }
      ];
    },  
    initData: function() {
      
        this.papa = this.initConfig().papa;
        this.store =  Ext.create('Ext.data.JsonStore', {
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: '/api/users',
                reader: {
                    type: 'json',
                    root: 'data',
                    idProperty: 'id'
                }
            },
            fields: [
              {name: 'id', type: 'int'}, 
              {name: 'user_type'},
              {name: 'login'}, 
              //{name: 'pass'}, 
              {name: 'activ_flag'},
              {name: 'last_visit'}
            ]
//            ,listeners: { scope: this,
//              load: function() {  },
//              beforeLoad: function() {  }
//            }
        });
//        this.listeners = { scope: this,
//              selectionchange: function(rm, record, idx, opt) {
////                  var subsId = record[0].data.subscription_id;
////                  this.papa.charges.loadData(subsId, 1, 100);
//              }
//        };
        
//        this.bbar = [
//            
//            {xtype: 'label', text: 'Всего найдено: ',  style: 'color: #04408c;'},
//            Ext.create('Ext.form.Label', {text: '0', id: 'subsCount'}),
//            {xtype: 'label', text: ' подписок ',  style: 'color: #04408c;'},
//            '-',
//            Ext.create('Ext.form.Label', {text: 'Показаны: с ',  style: 'color: #04408c;', id: 'll1'}),
//            Ext.create('Ext.form.Label', {text: '1', id: 'subsFirst'}),
//            Ext.create('Ext.form.Label', {text: ' по ',  style: 'color: #04408c;', id: 'll2'}),
//            Ext.create('Ext.form.Label', {text: '100', id: 'subsMax'}),
//            '->',
//            Ext.create('Ext.Button', {text: 'prev', icon: '/extjs/img/prev.gif', scope: this, disabled: true, id: 'prevButt',
//                 handler: function() {
//                    var first = this.queryParams.first;
//                    var max = this.queryParams.max;
//                    if(first - max < 1) this.queryParams.first = 1;
//                    else this.queryParams.first = first - max;
//                    Ext.getCmp('firstField').setValue(this.queryParams.first);
//                    
//                    this.loadData(this.queryParams);
//                 }
//            }),
//            '-',
//            Ext.create('Ext.Button', {text: 'next', icon: '/extjs/img/next.gif', scope: this, disabled: true, id: 'nextButt',
//                 handler: function() {
//                    var first = parseInt(this.queryParams.first);
//                    var max = parseInt(this.queryParams.max);
//                    var total = parseInt(Ext.getCmp('subsCount').text);
//                    if( (first+max) >= total) this.queryParams.first = (total - max +1);
//                    else this.queryParams.first = first + max;
//                    Ext.getCmp('firstField').setValue(this.queryParams.first);
//                    this.loadData(this.queryParams);
//                 }
//            })
//            
//        ];
        //this.showLabels(false);
    },
//    loadData: function(data) {     
//      this.mask();
////      this.queryParams = data;
////      this.papa.charges.store.removeAll();
//      Ext.Ajax.request({
//        url: '/api/users', scope: this, method: 'GET',
//        params: data,
//        success: function(response, opts) {
//          this.unmask();
//          var ansv = Ext.decode(response.responseText);
//          if(ansv.success) {  
//            this.store.loadData(ansv.data);            
//            this.count = this.store.count();
//            
////            Ext.getCmp('subsCount').setText(ansv.pagination.totalCount);
////            Ext.getCmp('subsFirst').setText(data.first);
////            Ext.getCmp('subsMax').setText(parseInt(data.first)+parseInt(this.count)-1);
////
////            this.showLabels(this.count>0);
////            
////            if(data.first == 1) Ext.getCmp('prevButt').setDisabled(true);
////            else Ext.getCmp('prevButt').setDisabled(false);
////            
////            if(parseInt(data.first)+parseInt(this.count) >= parseInt(ansv.pagination.totalCount)) Ext.getCmp('nextButt').setDisabled(true);
////            else Ext.getCmp('nextButt').setDisabled(false);
//            
//          } else error_mes('Ошибка', 'ErrorCode:'+ansv.error.errorCode+"; "+ansv.error.errorMessage);  
//        },
//        failure: function() { this.unmask(); }
//      });
//    },
//    unsubscribe: function(serviceCode, serviceName) {
//        var msisdn = Ext.getCmp('msisdnField').getValue();
//        
//        Ext.Msg.show({
//            title:'Внимание!',
//            msg: 'Вы действительно хотите закрыть подписку абонента <span style="color: red;">'
//                +msisdn+'</span> на сервис <span style="color:green;">'
//                +serviceName+'</span>?',
//            buttons: Ext.Msg.YESNO,
//            fn: function(btn){
//                 if(btn == "yes") {
//                    Ext.Ajax.request({
//                        url: '/subscription/unsubscribe?msisdn='+msisdn+'&service_code='+serviceCode, 
//                        method: 'DELETE',
//                        //params: {msisdn: msisdn, service_code: serviceCode},
//                        scope: this,
//                        callback: function(options, success, response) {
//                            var ansv = Ext.decode(response.responseText);
//                            if(ansv.success) {
//                                Ext.Msg.alert("Отписка", "Запрос на отписку номера "+msisdn+" от сервиса "+serviceName+" успешно отправлен.");
//                            } else {
//                                error_mes('Ошибка', ansv.error.errorMessage);
//                            }
//                        }
//                    });
//                 }
//            },
//            animEl: 'elId',
//            icon: Ext.MessageBox.QUESTION
//        });
//    },
    statusRenderer: function(val) { 
        if(val=='Y') return 'активный'; 
        else return 'не активный'; 
    },
    dateRenderer: function(val) {
      return Ext.util.Format.date(val, 'd.m.Y H:i:s');
    }//,
//    showLabels: function(show) {
//        if(show) {
//            Ext.getCmp('ll1').show();
//            Ext.getCmp('ll2').show();
//            Ext.getCmp('subsFirst').show();
//            Ext.getCmp('subsMax').show();
//        } else {
//            Ext.getCmp('ll1').hide();
//            Ext.getCmp('ll2').hide();
//            Ext.getCmp('subsFirst').hide();
//            Ext.getCmp('subsMax').hide();
//        }
//    }
    
});

