
oms.project={}; // project builder
// this will return a fieldSets

// panel for create new project
oms.project.createNewProjPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Create New Project',
	bodyPadding: 10,
	width:800,
	scrollable:true,
	closeAction: 'hide',
	layout:'hbox',
	items:[
		{
		xtype:'form',
		id: 'createNewProject',
		border:0,
		defaultType:'textfield',
		layout:'vbox', 
		bodypadding:'10 10 10 10',
		items:[
			{
				name:'projName', 
				fieldLabel:'Project Name:',  
				value:'New Project',
				margin: '0 2 2 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle',
				allowBlank:false
			},
			{
				name:'projAgency', 
				fieldLabel:'Project Agency:',  
				margin: '0 2 2 15',
				labelCls:'omslabelstyle',
				allowBlank:false,
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projOrg', 
				fieldLabel:"Organization:",
				allowBlank:false,
				margin: '0 2 2 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
				},
				{ 
					name:'projloc',
					fieldLabel:'Project Location',
					allowBlank:false,
					margin: '0 2 2 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle' 
					},
					{
					name:'contactoffice', 
					allowBlank:false,
					fieldLabel:'Contact Office',
					margin: '0 2 2 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'

					},
					{
					name:'projcategory',
					fieldLabel:'Category',
					margin: '0 2 2 15',
					labelCls:'omslabelstyle',
					allowBlank:false,
					fieldCls:'omsfieldstyle'
					},
					{

					name:'isPrimeProject',
					fieldLabel:'Through Prime7',
					xtype:'checkbox',
					value:0,
					inputValue:true,
					uncheckValue:false,
					margin: '0 2 2 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
					},
					{
					name:'primeName',
					fieldLabel:'Prime Name:',
					margin: '0 2 2 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
					},
					{
					name:'projduedate',
					fieldLabel:'Target Date', 
					margin: '0 2 2 15',
					xtype:'datefield',
					labelCls:'omslabelstyle',
					allowBlank:false,
					fieldCls:'omsfieldstyle'
						
					},
					{
						xtype:'combobox',
						margin: '0 2 2 15',
						id:'ptcombo',
						name: 'projTempId',
						valueField:'id',
						displayField:'name',
						queryMode: 'local',
			            typeAhead: true,
			            allowBlank:false,
						fieldLabel:'Project Template',
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle',
						store:Ext.create('Ext.data.JsonStore', {
							fields: [
								{name: 'id' },
								{name: 'name'}
								]
						}),
						listeners:{
							change:{
								elment:'el',
								fn:function(obj,newV,oldV,ops){
									//Ext.Msg.alert('alert',newV);
									var rec=obj.getSelection();
									var data=Ext.decode(rec.data.config);
									//console.log(data);
									var store=Ext.getCmp('createProjTasksGrid').getStore();
									//console.log(store);
									store.setData(data.tasks);
									Ext.getCmp('createProjTasksGrid').getSelectionModel().selectAll(true);
								}							
							}
						}
					}
				]
		},
		{
			width:'60%', frame:true,border:1,
			xtype:'grid',
			id:'createProjTasksGrid',
			selType: 'checkboxmodel',
			minHeight:120,
			//hideHeaders:true,
			columnLines:false,
			store:Ext.create('Ext.data.JsonStore', {
				fields: [
					{name: 'id' },
					{name: 'name'}
					]
			}),
			columns: [
				{text:'ID',dataIndex:'id',width:'10%'},
				{text: "Task Name", dataIndex: 'name',width:'70%'}		
				]
		}
		],
	buttons:[
		{
			text:"Create New Project",
			handler: function(){
				var form=Ext.getCmp('createNewProject').getForm();
				if(form.isValid())
					{
						var formdata = form.getValues();
	                   //Ext.getCmp("userEditform").getForm().getValues();
						console.log(formdata);
						// now get the grid task template ID data
						var grid=Ext.getCmp('createProjTasksGrid');
						var l=grid.getSelectionModel().getSelection();
						var tasks=[];
						if(l!=null&&l.length>0)
						{
							for(var i=0;i<l.length;i++)
							{
								var task=oms.admin.lookupTaskDetails(l[i].data.id);
								if(task!=null)
									{
										tasks[tasks.length]=task;
									}
							}
						}
						var projjson={"projectInfo":formdata,"tasks":tasks};
						console.log(projjson);
						
						Ext.Ajax.request({
							url : "api/project/newProject",
							method : 'POST',
							jsonData : JSON.stringify(projjson),
							success : function(response, option) {
								console.log(response);
								var respObj = Ext.decode(response.responseText);
								Ext.Msg.alert(respObj.status, respObj.message);
								if (respObj.status === 'success') {
									oms.admin.DoctypeEditPanel.hide();
								}
							},
							failure : function(response, option) {
								console.log(response);
								Ext.Msg.alert('Error', response.responseText);
							}
						});
					}
				
				/*
				Ext.Ajax.request({
					url : "api/project/newProject",
					method : 'POST',
					jsonData : JSON.stringify(formdata),
					success : function(response, option) {
						console.log(response);
						var respObj = Ext.decode(response.responseText);
						Ext.Msg.alert(respObj.status, respObj.message);
						if (respObj.status === 'success') {
							oms.admin.DoctypeEditPanel.hide();
						}
					},
					failure : function(response, option) {
						console.log(response);
						Ext.Msg.alert('Error', response.responseText);
					}
				});
				*/
				
				
			}
		}
	]
});



oms.project.makeLastUpdateHtml=function()
{
var res="<ul style='font-size:8pt;color:#336699'>";
res+="<li><b>Note added by John Smith on 01/13/2017</b> <p>";
res+="<font color=green> RFP Draft requirements analysis completed</font></li>";
res+="<li><b>Note added by Janet Hassen on 12/03/2016</b> <p>";
res+="<font color=green> NDA Agreement send out to contractors waiting for their signature. Turn arround time is arround 1 weeks.</font></li>";
res+="<li><b>RFP Draft added by Bajie on 11/13/2016</b> <p>";
res+="<a href=''>SVT Tracking System -- RFP Draft</a>";
res+="</ul>";
return res;
};

oms.project.createProjLastUpdate=function(proj)
{
var panel=Ext.create('Ext.form.Panel',{
//title:"Info",
width:'98%',
margin:'10 0 0 4', 
header:{
baseCls:'omspanelheadercls',
items:[{xtype:'label',html:'<span style="font-size:10pt"><b>Project Last Update</b></span>',width:'95%'}]
},
html:oms.project.makeLastUpdateHtml(),
tools:[
{type:'refresh'} 
],
});
return panel;
};
oms.project.createProjInfoPanel=function(pinfo) // json object of the project info
{
var panel=Ext.create('Ext.form.Panel',{
//title:"Info",
width:'98%',
margin:'0 0 0 4', 
collapsible:true,
header:{
baseCls:'omspanelheadercls',

items:[{xtype:'label',html:'<span style="font-size:10pt"><b>Project Info</b></span>',width:'85%'}]
},
tools:[
{type:'gear'},
{type:'refresh'} 
],
//buttonAlign:'left',
defaultType:'displayfield',
layout:'vbox', 
bodypadding:'10 10 10 10',

items:[
{
name:'projAgency', 
fieldLabel:'Project Agency:', 
value:'DOE', 
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{
name:'projOrg', 
fieldLabel:"Organization:",
value:'Orgnaization',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{
name:'projSubOrg', 
fieldLabel:'Sub Organization:',
value:'Sub Org',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'

},
{ 
name:'projloc',
fieldLabel:'Project Location',
value:'San Antonio, TX',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle' 


},
{
name:'contactoffice', 
fieldLabel:'Contact Office',
value:'office contact',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'

},
{
name:'projcategory',
fieldLabel:'Category',
value:'IT Security',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{

name:'isPrimeProject',
fieldLabel:'Through Prime',
value:'true',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{
name:'primeName',
fieldLabel:'Prime Name:',
value:'TCL',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{
name:'projduedate',

fieldLabel:'Target Date', 
margin: '0 2 0 15',
value:'01/01/2017',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'

}, 
{ 
name:'projstartdate',
fieldLabel:'Start Date', 
value:'01/01/2017',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
},
{ 
name:'projcompletedate',
fieldLabel:'Complete Date', 

value:'01/01/2017',
margin: '0 2 0 15',
labelCls:'omslabelstyle',
fieldCls:'omsfieldstyle'
} 
]
});
return panel;
};

oms.project.createTaskItemPanel=function(taskdata)
{
// build header
var h_html='<table width=100% style="background-color:#eeeeff;font-size:8pt"><tr>';
h_html=h_html+"<td width=30>#"+taskdata[1]+"</td>";
h_html=h_html+"<td><b>Task:</b><font color=green>"+taskdata[2]+"</font></td>";
h_html=h_html+"<td width=15%><b>Owner:</b><font color=#336699>"+taskdata[3]+"</font></td>"; 
h_html=h_html+"<td width=15%><b>Status:</b><font color=green>"+taskdata[4]+"</font></td>";
h_html=h_html+"<td width=20%><b>Target Date:</b><font color=green>"+taskdata[5]+"</font></td>";
h_html=h_html+'<td width=10%><img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif"><img src="css/images/shared/icons/fam/information.png"></td>';
h_html=h_html+"</tr></table>";
var taskid="task"+taskdata[0];
// now build commend and document panel
var clgrid=oms.task.createTaskCommentPanel(taskdata,taskid);
var dlgrid=oms.task.createTaskDocumentPanel(taskdata,taskid); 
//console.log(clgrid);
var taskpanel=Ext.create('Ext.panel.Panel',{
id:taskid,
scrollable:true,
header:{items:[{xtype:'label',width:'98%',html:h_html}]},
// title:'task 1',
collapsed: true,
collapsible: true, 
titleCollapse: true,
width:'99%',
layout:'vbox', 
items:[
{
xtype:'tabpanel',
id:'taskDetails'+taskid,
width:'90%',
// tabPosition: 'left',
margin:'2 2 2 22', 
flex:1, 
defaults:{
bodypadding:10,
scrollable:true,
border:true
}, 
items:[
dlgrid,
clgrid,
{title:'Related Content',
html:oms.ui.getSearchHtml()

} 
]
}
]
});

return taskpanel;
};

oms.project.createTaskListPanel=function(taskList,projID)
{
var pc=Ext.create('Ext.panel.Panel',{
id:'tasklistpanel'+projID, 
title:'Tasks List', 
items:[ 
]
}); 

var data=oms.project.tasklistsample;
//console.log(data);
for(var i=0;i<data.length;i++)
{
//console.log(data[i]);
var itempanel=oms.project.createTaskItemPanel(data[i]);
if(data[i][4]=='in progress')
{
itempanel.collapsed=false;
}
pc.add(itempanel);
}
//pc.doLayout();

return pc;

/*
var tlstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'tlStore',
fields: [
{name: 'taskID'},
{name: 'seq'},
{name: 'taskname'}, 
{name: 'owner' },
{name: 'status'},
{name: 'targetdate', type: 'date'},
{name: 'startdate',type: 'date'},
{name: 'completedate',type:'date'}
],
data:oms.project.tasklistsample
});

var grid=Ext.create('Ext.grid.Panel',{
id:'taskgrid',
store:tlstore, 
scrollable:true,
columns: [
{text: "#", dataIndex: 'seq'},
{text: "Name", flex:1 ,dataIndex: 'taskname'},
{text: "Status", dataIndex: 'status',
renderer : function(val) { 
if (val =='completed') {
return '<span style="color:#339933">' + val + '</span>';
} else if (val =='past due') { 
return '<span style="color:red">' + val + '</span>';
}
else if (val =='in progress') { 
return '<span style="background-color:#ffff00">' + val + '</span>';
}

return val;
},
},
{text: "Target Date", dataIndex: 'targetdate',formatter: 'date("m/d/Y")'},
{text: "Owner", dataIndex: 'owner',width:160},
{text: "Action",dataIndex:"taskID",width:100,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif"><img src="css/images/shared/icons/fam/information.png">';
return html;

}
}
],
columnLines: true, 
header:{layout:'fit',items:[{xtype:'label',width:'96%',html:'<table width=100%><tr><td width=40>#1</td><td><font size=-1 color="green">#</font> -- <font color="#336699">Collect RFP from Customer</font></td></tr></table>'}],cls:'x-panel',border:'1 solid red'},
// title:'task 1',
collapsed: true,
collapsible: true,

plugins: [{
ptype: 'rowexpander',
rowBodyTpl : new Ext.XTemplate(
'<font color=green><p><b>Task ID:</b> {taskID} --- <b>start date:{startdate} -- <b>end date:</b> {completedate}</p></font>',
'<p><font color=#336699>Last updated: 01/03/2017 by Zhongkai -- Document review and comments uploaded </font></p>',
'Documents and Comments, please view <a href=""> Details </a>'
)
}],
});



var pc=Ext.create('Ext.panel.Panel',{
id:'tasklistpanel', 
title:'Tasks List', 
items:[ grid 
]
}); 
return pc;
*/ 
};

oms.project.createDocumentPanel=function(docList)
{
var dlstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'dlStore',
fields: [
{name: 'docid'}, 
{name: 'docname'},
{name: 'doctype'}, 
{name: 'task' },
{name: 'user'},
{name: 'uploaddate', type: 'date'},
{name: 'required'},
{name: 'restricted',type:'boolean'}
],
data:oms.project.doclistsample
});
var grid=Ext.create('Ext.grid.Panel',{
id:'docgrid',
store:dlstore, 
scrollable:true,
columns: [
{text:"",dataIndex:'restricted', width:40,
renderer:function(val)
{
if(val==true)
{
return "<img src='css/images/sec.jpg' width=20 />"; 
}
return "";
} 
},
{text: "type", dataIndex: 'doctype',width:180},
{text: "Name", flex:2,dataIndex: 'docname'}, 
{text: "Task", flex:1,dataIndex: 'task'},
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
var html='<img src="css/images/shared/icons/fam/information.png">';
return html;

}
}
],
columnLines: true, 
title:' Documents ' 
});
return grid;
};

oms.project.createUserPanel=function(userlist)
{
var ulstore=Ext.create('Ext.data.ArrayStore', {
// store configs
storeId: 'ulStore',
fields: [
{name: 'userid'}, 
{name: 'username'},
{name: 'role'},
{name: 'restricted',type:'boolean'}
],
data:oms.project.userlistsample
});
var grid=Ext.create('Ext.grid.Panel',{
id:'usergrid',
store:ulstore, 
scrollable:true,
columns: [
{text:"",dataIndex:'restricted', width:40,
renderer:function(val)
{
if(val==true)
{
return "<img src='css/images/sec.jpg' width=20 />"; 
}
return "";
} 
},
{text: "Name",flex:2, dataIndex: 'username'}, 
{text: "Role", flex:1, dataIndex: 'role'}, 
{text: "Action",dataIndex:"taskID",width:120,
renderer:function(val)
{
var html='<img src="css/images/shared/icons/fam/delete.gif"><img src="css/images/shared/icons/fam/user_edit.png">';
return html;

}
}
],
columnLines: true, 
title:' Team Members ',

tbar: [
{ text:'Add User' } 
]
});
return grid;
};
oms.project.createProjectPanel=function(proj) // proj is the json data for the project
{
var infop=oms.project.createProjInfoPanel(proj.info) ;
var tlgrid=oms.project.createTaskListPanel(proj.tasklist,1);
var dlgrid=oms.project.createDocumentPanel(proj.doclist);
var ulgrid=oms.project.createUserPanel(proj.userlist);
//console.log(infop);
var mainpanel=Ext.create('Ext.panel.Panel',{
id:"projectPanel"+proj.projectID,
layout:'hbox',
title:"PROJECT : -- ["+proj.name+"]",
padding:'5 5 5 5',
border:true,
items:[
{layout:'vbox',
width:'68%',
items:[
{
xtype:'fieldset',
// title:'Status and Manager',
border:0,
margin:'2 2 2 2',
layout:'hbox', 
height:30,
items:[
{
xtype:'displayfield',
name:'projstatus',
fieldLabel:'Project Status',
labelWidth:110, 
margin:'2 60 0 5',
value:'<span style="color:green; ">In Progress</span>' 
},
{
xtype:'displayfield',
name:'projmanager',
labelWidth:110,
fieldLabel:'Project Manager',
margin:'2 5 0 5',
value:'<font color="green">Alpha Delta</font>'
},
{
xtype:'button',
margin:'2 5 0 5',
scale:'small',
text:'Assign'
}
] 
},
{
xtype:'tabpanel',
id:'projectDetails',
width:'99%',
margin:'2 2 2 2',
flex:1, 
defaults:{
bodypadding:10,
scrollable:true,
border:true
},
tabPosition: 'left', 
items:[
tlgrid, 
dlgrid,
ulgrid
]
}
]
},
{
xtype:'container',
width:'30%',
layout:'vbox',
items:[
infop,
oms.project.createProjLastUpdate()
]
}

]
}
);

return mainpanel;
};

　
oms.project.sample1={
projectID:1,
name:'SVT Tracking System',
pmID:12,
pmName:'Alpha Go',
info:{},
tasklist:{},
doclist:{},
userlist:{}
};

oms.project.doclistsample=
[
[31,"Request for Proposal","RFP","Upload RFP Task","John Smith","12/01/2016","Y",false],
[32,"RFI","RFI","Upload RFP Task","Su Wukong","12/03/2016","N",false],
[33,"NDR","NDR","Study Requirement",null,null,"Y",false],
[34,"Proposal Draft","Proposal Draft","Merge Proposal Draft","Su Wukong","12/03/2016","Y",,false],
[35,"Proposal Draft Review","Proposal Review","Review Proposal Draft","Su Wukong","12/03/2016","N",false],
[36,"Proposal Draft with financial","Proposal Draft FIN","Merge Proposal Draft","Su Wukong","12/03/2016","O",true],
[37,"Proposal Draft Review FIN","Proposal Review FIN","Proposal Review FIN","Su Wukong","12/03/2016","O",true],
[39,"Proposal Production","Proposal Prod","Production","Su Wukong","12/03/2016","Y",false], 
[40,"Proposal Production with financial","Proposal Prod FIN","Production","Su Wukong","12/03/2016","Y",true],
];

oms.project.tasklistsample=
[
[100,1,"Create Project","Steve Hopkins","completed","01/01/2017","12/02/2016","12/23/2016"],
[101,2,"Draft RFP Management","Steve Hopkins","completed","01/01/2017","12/02/2016","12/23/2017"],
[102,3,"Teaming","Steve Hopkins","past due","01/01/2017","12/02/2016",null],
[103,4,"Final RFP Management","Steve Hopkins","in progress","01/01/2017",null,null],
[104,5,"Proposla Preparation","Steve Hopkins","not started","01/01/2017",null,null],
[105,6,"1st Draft Prep","Steve Hopkins","not started","01/01/2017",null,null],
[106,7,"Pink Team Review","Steve Hopkins","not started","01/01/2017",null,null],
[108,8,"2nd Draft Prep","Steve Hopkins","not started","01/01/2017",null,null],
[109,9,"Red Team Review(RTR)","Steve Hopkins","not started","01/01/2017",null,null],
[110,10,"Final Prep","Steve Hopkins","not started","01/01/2017",null,null],
[111,11,"Submission","Steve Hopkins","not started","01/01/2017",null,null],
[112,12,"Post Submittal Management","Steve Hopkins","not started","01/01/2017",null,null],
[114,13,"Contract Award","Steve Hopkins","not started","01/01/2017",null,null]
]; 

oms.project.userlistsample=
[
[1,"Seng Tang","Project Manager",true],
[2,"Wukong Sun","Reviewer",true],
[3,"Bajie Zhu","Restricted User",true],
[4,"Seng Sha","Restricted User",true],
[5,"YaoGuai 1","User",false],
[6,"YaoGuai 2","Contractor",false]
];