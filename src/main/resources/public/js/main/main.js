Ext.onReady(function(){
  
  Ext.BLANK_IMAGE_URL = '/extjs/img/s.gif';

  var MainToolBar = {
      region: 'south', xtype: 'toolbar', height: 30, border: true,
      items: [
          {xtype: 'tbtext', text: '&nbsp;Имя пользователя: <b>admin</b>'}, '->',
          {text: 'Выход', handler: CloseWin, id: 'close-btn',icon: '/extjs/img/exit.png'}
      ]
  };
  
  var MainTab  = Ext.create('MainTab');
  var SettingsTab  = Ext.create('SettingsTab');
  //var ReportTab  = Ext.create('ReportTab');
  
  var MainPanel  = Ext.create('Ext.tab.Panel',{
    region: 'center',
    items: [MainTab, SettingsTab ]
  });
  
  var delimHr = { xtype: 'box', html:'', style: 'border-bottom: 1px solid #99BCE8;', height: 5};
  var delim = { xtype: 'box', html:'',height: 5};

  Ext.create('Ext.container.Viewport', {
    layout: 'border', 
    items: [ MainToolBar, MainPanel ]
  });//.render('vp');
 

  //ButtonPanel.updateSate();
//  
//  Ext.getDom('boby').onmousemove = function() { slCounter = 0; };
//  slCounter = 0;
//  screenLockT=setTimeout(screenLock, 60000);
});