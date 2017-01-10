oms.task={}; // project builder
// this will return a fieldSets
oms.task.createTaskInfoPanel=function(tinfo) // json object of the project info
{
var panel=Ext.create('Ext.form.Panel', {
// title:"Project Information:",
width:'99%',
margin:'2 2 2 2',
buttons:[
{text:"Save"},
{
text:"Update Status"
}
],
items:[
{
xtype:'fieldset',
title:'Basic Info',
margin:'2 2 2 2',
layout:'hbox', 
items:[
{ 
xtype:'textfield',
name:'taskname',
labelWidth:110,
fieldLabel:'Task',
margin:'0 15 0 5',
value:'Review Proposal Draft'
},
{
xtype:'displayfield',
name:'pname',
fieldLabel:'Project',
labelWidth:80,
margin:'0 5 0 5',
value: '<font color=#336699>SVT Tracking System</font>' 
},

{
xtype:'button',
text:'View Project'
}
] 
}, 
{
xtype:'fieldset',
title:'Status and Owner',
margin:'2 2 2 2',
layout:'hbox', 
items:[
{
xtype:'displayfield',
name:'projstatus',
fieldLabel:' Status',
labelWidth:110,
margin:'0 60 0 5',
value:'<span style="color:green; ">In Progress</span>' 
},
{
xtype:'textfield',
name:'owner',
labelWidth:110,
fieldLabel:'Task Owner',
margin:'0 10 0 5',
value:'Alice'
},
{
xtype:'button',
text:'Assign'
} 
] 
}, 
{
xtype:'fieldset',
layout:'hbox',
title:'Dates Info', 
margin:'2 2 5 5',
defaultType:'datefield', 
items:[{
name:'projduedate',
fieldLabel:'Target Dilvery Date', 
labelWidth:120,
margin:'0 5 0 5'
}, 
{ 
name:'projstartdate',
fieldLabel:'Start Date',
labelWidth:110,
margin:'0 5 0 5'
},
{ 
name:'projcompletedate',
fieldLabel:'Complete Date',
labelWidth:110,
margin:'0 0 5 5'
} 
] 
},
{
xtype:'fieldset',
layout:'hbox',
title:'Review Info', 
margin:'2 2 5 5',
defaultType:'textfield', 
items:[{
name:'reviewFlag',
xtype:'checkbox',
fieldLabel:'Required Review', 
labelWidth:110,
margin:'0 5 0 5'
}, 
{ 
name:'reviewer',
fieldLabel:'Reviewer', 
margin:'0 5 0 5',
labelWidth:80,
disabled:true
},
{
xtype:'button',
text:'Assign',
margin:'0 5 0 15',
disabled:true
},
{
xtype:'button',
text:'Reject With Comments',
margin:'0 5 0 15',
disabled:true 
},
{
xtype:'button',
text:'Accept',
margin:'0 5 5 15',
disabled:true
}

] 
}
]
});
return panel;
};

oms.task.createTaskCommentPanel=function(comments)
{
// first load the store
var clstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'clStore',
fields: [
{name: 'seq'},
{name: 'user' }, 
{name: 'commentdate', type: 'date'},
{name: 'commenttitle'},
{name: 'commentcontent'}
],
data:oms.task.commentlist
});
var grid=Ext.create('Ext.grid.Panel',{
id:'commentgrid',
store:clstore, 
scrollable:true,
tbar:[
{text:"Add Comment"}
],
columns: [
{text: "#", dataIndex: 'seq'},
{text: "Title",flex:1, dataIndex: 'commenttitle'},
{text: "User", flex:2, dataIndex: 'user',width:160},
{text: "Date", dataIndex: 'commentdate',formatter: 'date("m/d/Y")'},
{
text:"",dataIndex:"seq",
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/user_edit.png">';
return html;

} 
}
],
columnLines: true, 
title:'Comments',
plugins: [{
ptype: 'rowexpander',
rowBodyTpl : new Ext.XTemplate(

'<font color=#336699><h3>Content</h3><p></font><hr>', 
'<font color=green>{commentcontent}</font>'
)
}],
});
return grid;
};

oms.task.createTaskDocumentPanel=function(docList)
{
var dlstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'taskdlStore',
fields: [
{name: 'seq'}, 
{name: 'docid'},
{name: 'docname'},
{name: 'doctype'}, 
{name: 'user'},
{name: 'uploaddate', type: 'date'},
{name: 'required'},
{name: 'restricted',type:'boolean'}
],
data:oms.task.doclistsample
});
var grid=Ext.create('Ext.grid.Panel',{
id:'taskdoclistgrid',
store:dlstore, 
scrollable:true,
tbar:[
{
text:"Add New Document" 
}],

columns: [
{text:"",dataIndex:'restricted', width:40,
renderer:function(val)
{
if(val==true)
{
return "<img src='css/images/sec.png' width=20 />"; 
}
return "";
} 
},
{text: "Name", flex:2,dataIndex: 'docname'},
{text: "type", dataIndex: 'doctype',width:180}, 
{text: "Upload Date", dataIndex: 'uploaddate',formatter: 'date("m/d/Y")'},
{text: "User", dataIndex: 'user',width:160}, 
{text: "Req.",dataIndex:"required",width:50,
renderer:function(val)
{
if(val=="Y")
{
return '<font color=red>'+val+'</font>'
}
return val;
}
},
{text: "Action",dataIndex:"taskID",width:60,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/information.png"><img src="css/images/upload.png" width=20>';
return html;

}
}
],
columnLines: true, 
title:' Documents ' 
});
return grid;
};

　
oms.task.createTaskMainPanel=function(task) // proj is the json data for the project
{
var infop=oms.task.createTaskInfoPanel(task.taskinfo) ;
var clgrid=oms.task.createTaskCommentPanel(task.commentlist);
var dlgrid=oms.task.createTaskDocumentPanel(task.doclist);
//var ulgrid=oms.project.createUserPanel(proj.userlist);
//console.log(infop);
var mainpanel=Ext.create('Ext.panel.Panel',{
id:"taskPanel"+task.taskID,
layout:'vbox',
title:"TASK : -- ["+task.taskname+"]",
border:true,
items:[
infop,
{
xtype:'tabpanel',
id:'taskDetails',
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
clgrid,
dlgrid
]
}
]

});

return mainpanel;
};

　
oms.task.sample1={
projectID:1,
taskID:15,
projectname:'SVT Tracking System',
taskname:'Review Proposal Draft',
pmID:12,
pmName:'Alpha Go',
taskinfo:{},
commentlist:{},
doclist:{} 
};

oms.task.doclistsample=
[
[34,10,"Proposal Draft","Proposal Draft","Su Wukong","12/13/2016","Y",,false],
[35,null,null,"Proposal Review","Su Wukong",null,"Y",false],
[36,null,null,"Proposal Draft FIN","Su Wukong",null,"Y",true],
[37,null,null,"Proposal Review FIN","Su Wukong",null,"Y",true],
[38,11,"Aemndment 1","RFP Amendment","Su Wukong","12/20/2016","O",false],
];

　
oms.task.commentlist=
[
[1,"Seng Tang","03/20/2016","comment title 1","comment text 1, blar blar blar"],
[2,"Wu Kong","02/12/2016","comment title 2","comment text 2, blar blar blar"],
[3,"Wu Kong","02/11/2016","comment title 3","comment text 3, blar blar blar"],
[4,"Ba Jie","02/02/2016","comment title 4","comment text 4, blar blar blar"],
[5,"Seng Tang","01/02/2016","comment title 5","comment text 5, blar blar blar"],
[6,"Seng Tang","01/01/2016","comment title 6","comment text 6, blar blar blar"]
];