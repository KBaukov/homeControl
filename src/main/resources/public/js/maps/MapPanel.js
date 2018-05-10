Ext.define('MapPanel', {
    extend: 'Ext.panel.Panel',
    initComponent: function() {
        this.border = true;
        this.frame = true;
        this.region = 'center';
        this.margins = '2 2 2 2';
        this.collapsible = true;
        this.collapsed = false;
        this.resizable = false;
        this.autoScroll = true;
        this.bodyPadding = 10;
        this.bodyStyle = 'padding:10px; background: #ffffff'; //#cbddf3;';
        
        this.initForm();
        
        MapPanel.superclass.initComponent.apply(this, arguments);
    },
    initForm: function() {
        this.mapData = this.initConfig().mapData;
        this.title = this.mapData.title;
        this.id = 'map'+this.mapData.id;
        this.tpl = [
            '<div style="text-align:center">','<img id="mapImage_{id}" src="{pict}" />','</div>',
            '<tpl for="sensors">',
                '<div class="{type}" id="ht{id}"></div>',
            '</tpl>'
        ];
        this.listeners = { scope: this,
            render: function() {
                this.getSensorsData();
            },
            resize: function() {
                this.resizeImage();
            }
        };
    },
    resizeImage: function() {
        var h = this.getHeight();
        var w = this.getWidth();
        var winRatio = w/h;
        var imgW = w - 40;
        if( winRatio>=1.3 ) {
            imgW = (h-40)*1.3;
        }
        if(this.data)
            var el = Ext.getDom('mapImage_'+this.mapData.id);    
        if(el) {
            el.width=imgW;
            var imgH = imgW / 1.3;
            var dw = (w - imgW) /2;
            var n = this.data.sensors.length;
            for(var i=0; i<n; i++) {
                var ht =  Ext.getDom('ht'+this.mapData.sensors[i].id );
                ht.style.top  = (20 + imgH * this.mapData.sensors[i].yk)+'px';
                ht.style.left = (dw + imgW * this.mapData.sensors[i].xk)+'px';
            }
        }
    },
    setContent: function(data) {
        this.data = data;
        this.update(data);
        this.resizeImage();
    },
    getSensorsData: function() {
        Ext.Ajax.request({
            url: '/api/sensors', scope: this, method: 'GET',
            params: { map_id: this.mapData.id },
            success: function(response, opts) {
              //this.unmask();
              var ansv = Ext.decode(response.responseText);
              if(ansv.success) {  
                this.mapData.sensors = ansv.data;
                this.setContent(this.mapData);
              } else error_mes('Ошибка', '!!!!!!!!!!!!!!!!!!!!!!!');  
            },
            failure: function() { this.unmask(); }
        });
    }
});