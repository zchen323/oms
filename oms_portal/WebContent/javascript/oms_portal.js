oms={};
oms.admin={};
oms.projmgr={};
oms.userpref={};

function calnextyear(start,rate,addon,addonrate)
{
var res=start*rate+addon*addonrate;
return res;
}

function displayRate()
{
var start=50000;
var rate=1.36;
var addon=20000;
var addonrate=1.18;
var res=start;
for(var year=2017;year<=2027;year++)
{
res=calnextyear(res,rate,addon,addonrate);
console.log("year of "+year+" .... "+res);
}
}
Ext.define('com.ccg.oms.portalHeader', {
extend: 'Ext.Container',
xtype: 'oms-portal-header',
title: document.title,
cls: 'app-header',
height: 40,
layout: {
type: 'hbox',
align: 'middle'
},
items: [{
xtype: 'component',
cls: 'oms-app-logo'
}, {
xtype: 'component',
cls: 'app-header-title',
html: document.title,
flex: 85 / 100
}, {
xtype: 'label',
id: 'userProfile',
flex: 15 / 100
}]
});

　
Ext.onReady(function(){
Ext.create('Ext.container.Viewport', {
layout: 'border',
renderTo : Ext.getBody(),
id: 'mainViewPort',
monitorResize: true,
items: [{
region: 'north',
xtype: 'oms-portal-header',
id: 'oms-app-header'
},{
region: 'west',
collapsible: true,
title: 'OMS Main Menu',
id: 'oms-nav-menu',
width: 240,
split: true,
layout: {
type: 'accordion',
pack: 'start',
align: 'stretch'
},
bodyPadding: 2,
defaults: {
bodyPadding: 2
},
items: [
{title:'Work Queue',
html:oms.ui.getWorkQueueHtml(12)
},
{title:'My Documents',
html:oms.ui.getUserDocumentHtml(12)
},
{title:'My Projects',
html:oms.ui.getUserProjectHtml(12)
}, 
{title:'Reports',
html:oms.ui.getReportHtml()
},
{title:'Admin',
html:oms.ui.getAdminHtml()
}
],
listeners:{
collapse:function(){
Ext.getCmp('centerViewPort').doLayout();
},
expand:function(){
Ext.getCmp('centerViewPort').doLayout();
}
}
},
{
region:'center',
id:'centerViewPort',
xtype:'tabpanel',
border:true,
frame:true,
defaults: {
bodyPadding: 10,
scrollable: true,
closable: true
},
items:[]
}]
});
// load sample project view

var ppanel=oms.project.createProjectPanel(oms.project.sample1);
var tpanel=oms.task.createTaskMainPanel(oms.task.sample1);
var dpanel=oms.doc.createDocMainPanel(oms.doc.sample1);
var adminpanel=oms.admin.createAdminMainPanel();
var viewport=Ext.getCmp('centerViewPort');
viewport.add(ppanel);
viewport.add(tpanel);
viewport.add(dpanel);
viewport.add(adminpanel);
viewport.doLayout();
viewport.setActiveTab(3);

});