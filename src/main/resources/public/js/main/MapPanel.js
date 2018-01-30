Ext.define('MapPanel', {
    extend: 'Ext.panel.Panel',
    initComponent: function() {
        this.title = 'Карта';
        this.border = true;
        this.frame = true;
        this.region = 'center';
        //this.height = 100;
        this.margins = '2 2 2 2';
        this.id = 'controlPanel';
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
        this.papa = this.initConfig().papa;
        this.html = '<div style="text-align:center"><img id="mapImage" src="/img/Map.png" /></div>';
        this.listeners = { scope: this,
            render: function() {
                this.resizeImage();
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
        Ext.getDom('mapImage').width=imgW;
    }
});