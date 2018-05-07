Ext.define('Devices', {
    extend: 'Ext.grid.Panel',
    initComponent: function() {
        this.title = 'Устройства';
        this.border = true;
        this.frame = false;
        this.region = 'center';
        this.height = 400;
        this.loadMask = true;
        this.emptyText  = 'Нет данных';
        this.margins = '2 2 2 2';
        this.id = 'devicesGrid';
        this.count = 0 ;
        this.collapsible = true;
        this.collapsed = false;
        this.resizable = true;
        
        this.selectedRec = null;
        
        this.initData();
        this.initColumns();
        //this.initForm();

        Devices.superclass.initComponent.apply(this, arguments);
    },
    initColumns: function() {
        
        this.columns = [            
            {header: 'ID', align: 'left', width: 40, dataIndex: 'id', flex: 1 }, 
            {header: 'Тип устройства', align: 'left', width: 120, dataIndex: 'type', editor: new Ext.form.TextField({ allowBlank: false }) },
            {header: 'Имя', align: 'left', width: 160, dataIndex: 'name', editor: new Ext.form.TextField({ allowBlank: false })  },
            {header: 'IP адрес', align: 'left', width: 160, dataIndex: 'ip', editor: new Ext.form.TextField({ allowBlank: false })  },
            {header: 'Статус', align: 'left', width: 110, dataIndex: 'active_flag', renderer: this.statusRenderer, editor: new Ext.form.TextField({ allowBlank: false })  },
            {header: 'Описание', align: 'left', width: 500, dataIndex: 'description', editor: new Ext.form.TextField({ allowBlank: true })  }
        ];
        this.plugins = [ Ext.create('Ext.grid.plugin.CellEditing', { clicksToEdit: 2 }) ];
        this.bbar = [
            Ext.create('Ext.Button', {text: 'Сохранить изменения', scope: this, disabled: false, id: 'editButt',
                style: 'background-position: bottom center;', 
                handler: function() { this.saveData(); }
            })//,
//            '->', '-',
//            Ext.create('Ext.Button', {text: 'Применить', scope: this, disabled: false, id: 'execButt',
//                style: 'background-position: bottom center;', 
//                handler: function() { this.sendCommands(); }
//            })            
        ];
    },
    initData: function() {
      this.papa = this.initConfig().papa;
      this.store =  Ext.create('Ext.data.JsonStore', {
          storeId: 'devicesData', autoLoad: true,   
            proxy: {
                type: 'ajax',
                url: '/api/devices',
                reader: {
                    type: 'json',
                    root: 'data',
                    idProperty: 'id'
                }
            },
          fields: [
            {name: 'id'}, {name: 'type'}, {name: 'name'}, {name: 'ip'},
            {name: 'active_flag'}, {name: 'description'}
          ]//,
      });
      
    },
    initForm: function () {
        this.editForm = Ext.create('Ext.form.Panel', {
            //title: 'Basic Form',
            bodyPadding: 5,
            width: 350,
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Тип устройства',
                    name: 'type'
                }
            ]
        });
        this.editWin = Ext.create('Ext.window.Window', {
            title: 'Форма редактирования устройства',
            height: 400, width: 400, layout: 'fit', modal: true,
            items: [  
                this.editForm
            ],
            buttons: [ 
                {
                    text: 'Сохранить',
                    scope: this,
                    handler: this.saveData
                },{
                    text: 'Отмена',
                    handler: this.closeWin
                }
            ]
        });
    },
    closeWin: function() { 
        this.editWin.close(); 
    },
    saveData: function() { 
        var i = 0;
    },
    loadData: function(subsId, start, cnt) { 
      this.mask();
      Ext.Ajax.request({
        url: '/api/users', scope: this, method: 'GET',
        params: {subscription_id: subsId, first: start, max: cnt },
        success: function(response, opts) {
          this.unmask();
          var ansv = Ext.decode(response.responseText);
          if(ansv.success) {  
            this.store.loadData(ansv.data);            
            this.count = this.store.count();
          } else error_mes('Ошибка', '!!!!!!!!!!!!!!!!!!!!!!!');  
        },
        failure: function() { this.unmask(); }
      });

    },
    statusRenderer: function(val) { 
        if(val=='Y') return 'активный'; 
        else return 'не активный'; 
    }
});

