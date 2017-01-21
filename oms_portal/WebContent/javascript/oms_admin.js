// this is the admin js
// Doctype Editing / Search type Editing / User Role editing /
// Task Template Editing
oms.admin.projectuserrole=[
["PM",true],
["BD",false],
["Proposal",false],
["Contractor",false] 
];
oms.admin.searchtype=[
[1,"Projects/Documents From related Customers","Customer Metadata"], 
[2,"Projects/Documents From related Categories","Project Metadata"],
[3,"Related Content Documents","Content"]
];
oms.admin.doctype=[
[1,"RFP Draft","Desc",null],
[2,"RFP Final","Desc",null],
[3,"NDA","Desc",null],
[4,"Proposal V1","Desc",null],
[5,"Proposal V2","Desc",null],
[6,"Proposal Final","Desc",null],
[7,"Review V1","Desc",null],
[8,"Review V2","Desc",null],
[9,"EN","Desc",null],
[10,"Contract","Desc",null]
];

oms.admin.users=[
[1,"ccg","CCGLast CCGFirst","ccg@ccg.com","PM",true,"01/01/2014"],
[2,"admin","Alast AFirst","admin@ccg.com","admin",true,"01/01/2015"],
[3,"user","ulast ufirst","user@ccg.com","user",true,"01/01/2016"]
];

oms.admin.users1=[
	[1,"ccg","CCGLast CCGFirst","ccg@ccg.com","PM",true,"01/01/2014"],
	[2,"admin","Alast AFirst","admin@ccg.com","admin",true,"01/01/2015"],
	[3,"user","ulast ufirst","user@ccg.com","user",true,"01/01/2016"]
	];

oms.admin.createRoleAdminPanel=function()
{
var rolestore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'roleStore',
fields: [ 
{name: 'role' }, 
{name: 'fullaccess',type:'boolean'} 
],
data:oms.admin.projectuserrole});

var grid=Ext.create('Ext.grid.Panel',{
id:'projrolegrid',
store:rolestore, 
scrollable:true,
tbar:[
{
text:"Add New Role" ,
listeners:{
click:
{
element:'el',
fn:function(){
oms.admin.userEditPanel.show();}
}
}
}
],

columns: [

{text: "Role Name", dataIndex: 'role'}, 
{text: "Restricted Access",dataIndex:'fullaccess'}, 
{text: "Action",dataIndex:"taskID",width:60,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" >';
return html;

}
}
],
columnLines: true, 
title:'Roles' 
});
return grid;
};
oms.admin.createUserAdminPanel=function()
{
var ustore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'ulistStore',
fields: [
{name: 'userID'},
{name: 'username' }, 
{name: 'name'},
{name: 'email'},
{name: 'role'},
{name: 'fullaccess',type:'boolean'},
{name: "createdTS",type:'date'}
],
data:oms.admin.users
});
var grid=Ext.create('Ext.grid.Panel',{
id:'userlistadmingrid',
store:ustore, 
scrollable:true,
tbar:[
{
text:"Add New User" ,
listeners:{
click:
{
element:'el',
fn:function(){
oms.admin.userEditPanel.show();}
}
}
}
],

columns: [
{text:"Restricted Access",dataIndex:'fullaccess', width:160,
renderer:function(val)
{
if(val==true)
{
return "<img src='css/images/sec.jpg' width=20 />"; 
}
return "";
} 
},
{text: "Username", flex:2,dataIndex: 'username',
renderer:function(val)
{
if(val==null)
{
return "<a href='#'>Upload Now</a>";
}
else{
return val;
}

}
},
{text: "Name", dataIndex: 'name',width:180}, 
{text: "Created", dataIndex: 'createdTS',formatter: 'date("m/d/Y")'},
{text: "Email", dataIndex: 'email',width:160}, 
{text: "Role.",dataIndex:"role",width:120,
renderer:function(val)
{
if(val=="Y")
{
return '<font color=red>'+val+'</font>';
}
return val;
}
},
{text: "Action",dataIndex:"taskID",width:60,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" >';
return html;

}
}
],
columnLines: true, 
title:'All Users' 
});
return grid;
};

oms.admin.createAdminPanel=function()
{
var ugrid=oms.admin.createUserAdminPanel();
var dtgrid=oms.admin.createDoctypeAdminPanel();
var stgrid=oms.admin.createSearchTypeAdminPanel();
var panel=Ext.create('Ext.panel.Panel',{
id:"adminMain",
layout:'vbox',
title:"OMS Admin",
border:true,
items:[
{
xtype:'tabpanel',
id:'adminDetails',
width:'99%',
margin:'2 2 2 2',
flex:1, 
defaults:{
bodypadding:10,
scrollable:true,
border:true
},
tabPosition: 'right', 
items:[
ugrid,
dtgrid,
stgrid, 
oms.admin.createTLPanel(null),
{title:'ProjectTemplate'}
// oms.admin.userEditPanel
]
}

]
});
return panel;
};

oms.admin.userEditPanel=Ext.create('Ext.window.Window',{
frame: true,
float:true,
closable:true, 
title: 'Edit User',
bodyPadding: 10,
scrollable:true,
closeAction: 'hide',
width: 360,
modal: false,
defaultType: 'textfield',
fieldDefaults: {
labelAlign: 'right',
labelWidth: 150,
msgTarget: 'side'
},
items:[
{ fieldLabel: 'User Name', name: 'user', emptyText: 'user id' },
{ fieldLabel: 'Password', name: 'pass', emptyText: 'password', inputType: 'password' },
{ fieldLabel: 'Verify', name: 'pass', emptyText: 'password', inputType: 'password' },
{
fieldLabel: 'Name',
emptyText: 'Name',
name: 'name'
}, 
{
fieldLabel: 'Company',
name: 'company'
}, 
{
fieldLabel: 'Email',
name: 'email',
vtype: 'email'
},
{ fieldLabel:'is Contractor',
xtype:'checkbox',
name:'iscontractor'
},
{
fieldLabel:'Role',
name:'role'
},
{
fieldLabel:'Restr. Access',
xtype:'checkbox',
name:'fullaccess'
}
],
buttons: [{
text: 'Save',
disabled: true,
formBind: true
}]

});

oms.admin.createSearchTypeAdminPanel=function()
{
var ststore=Ext.create('Ext.data.ArrayStore',{
storeId:'ststore',
fields:[
{name:'id'},
{name:'searchname'},
{name:'type'}
],
data:oms.admin.searchtype,
});

var grid=Ext.create('Ext.grid.Panel',{
id:'searchtypegrid',
store:ststore, 
scrollable:true,
tbar:[
{
text:"Add New Search" ,
listeners:{
click:
{
element:'el',
fn:function(){
oms.admin.userEditPanel.show();}
}
}
}
],
columns: [
{text: "Search ID",dataIndex: 'id',width:120},
{text: "Search Name", dataIndex: 'searchname',flex:1}, 
{text: "Type", dataIndex: 'type',width:200},
{text: "Action",width:60,index:'id',
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/delete.gif" >';
return html;

}
}
],
columnLines: true, 
title:'Search Types' 
});
return grid;
};
oms.admin.createDoctypeAdminPanel=function(){
var dtstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'dtStore',
fields: [
{name: 'dttypeID'},
{name: 'name' }, 
{name: 'desc'},
{name: 'sampleURL'},
],
data:oms.admin.doctype, 
});
var grid=Ext.create('Ext.grid.Panel',{
id:'dtadminngrid',
store:dtstore, 
scrollable:true,
tbar:[
{
text:"Add New DocType" ,
listeners:{
click:
{
element:'el',
fn:function(){
oms.admin.userEditPanel.show();}
}
}
}
],

columns: [

{text: "Doctype ID",dataIndex: 'dttypeID',width:120},
{text: "Name", dataIndex: 'name',width:180}, 
{text: "Description", dataIndex: 'desc'},
{text: "Sample URL", dataIndex: 'sampleURL',flex:1},
{text: "Action",dataIndex:"taskID",width:60,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/delete.gif" >';
return html;

}
}
],
columnLines: true, 
title:'Doctype Admin' 
});
return grid;
};