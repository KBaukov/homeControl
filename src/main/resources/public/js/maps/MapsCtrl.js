Ext.define('MapsCtrl', {
    extend: 'Ext.panel.Panel',
    initComponent: function() {
        this.title = 'Настройка карт';
        this.border = true;
        this.frame = false;
        this.region = 'center';
        this.height = 400;
        this.margins = '2 2 2 2';
        this.id = 'MapsCtrlPanel';
        this.count = 0;
        this.lastId = 0;
        this.collapsible = true;
        this.collapsed = false;
        this.resizable = true;
        
        this.selectedRec = null;
        
        this.init();
        this.items =  [ this.mapsGrid, this.mapSensorGrid ];

        MapsCtrl.superclass.initComponent.apply(this, arguments);
    },
    init: function() {
        this.mapsGrid = Ext.create('MapsGrid',{papa: this});
        this.mapSensorGrid = Ext.create('MapSensorsGrid',{papa: this});
    }
});

