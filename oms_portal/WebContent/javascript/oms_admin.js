oms.admin={};

oms.admin.createDocTypeEditPanel=function()
{
	var dtstore=Ext.create('Ext.data.ArrayStore', {
		// store configs
		storeId: 'dtStore',
		fields: [
		{name: 'doctypeid'}, 
		{name: 'doctype'}, 
		{name: 'restricted',type:'boolean'}
		],
		data:oms.admin.doctypelist
		});
	var grid=Ext.create('Ext.grid.Panel',{
		id:'doctypegrid',
		store:dtstore, 
		scrollable:true,
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
		{text: "ID", flex:1,dataIndex: 'doctypeid'},
		{text: "Name", flex:2,dataIndex: 'doctype'},
		{text: "Action",dataIndex:"taskID",width:120,
			renderer:function(val)
			{
			var html='<img src="css/images/shared/icons/fam/delete.gif"><img src="css/images/shared/icons/fam/user_edit.png">';
			return html;

			}
			}
		],
		columnLines: true, 
		title:'DocType Editting' ,
		width:'80%',
		tbar:[
			{text:"Add New Doctype"}
		]
		});
		return grid;
};
oms.admin.createTaskTmplEditPanel=function()
{
};

oms.admin.createProjectTmplEditPanel=function()
{
};

oms.admin.createUserEditPanel=function()
{
};


oms.admin.createAdminMainPanel=function()
{
	var dtpanel=oms.admin.createDocTypeEditPanel();
	var mainpanel=Ext.create('Ext.tab.Panel',{
		id:"adminmainpanel",
		layout:'fit',
		title:"OMS Admin Panel",
		border:true,
		tabPosition:'right',
		items:[
			dtpanel,
			{title:'Task Template Edit'},
			{title:'Project Template Edit'},
			{title:'User Edit'}
			]
		});	
    return mainpanel;
};
// sample data
oms.admin.doctypelist=[
	[1,'RFP',false],
	[2,'NDR',false],
	[3,'RFI',false],
	[4,'PROPOSAL DRAFT',false],
	[5,'PROPOSAL DRAFT FIN',true],
	[6,'Proposal Review',false],
	[7,'Proposal FIN Review',true],
	[8,'Suport Doc',false],
	[9,'Restricted Doc',true],
	[10,'Prod Proposal',false],
	[11,'Prod Proposal FIN',true],
	[12,'EN Checklist',false],
	[13,'Briefing Report',false],
	[14,'Awarded Contract',false]
];

oms.admin.tasktmplsample=
	[
		[1,"Upload RFP","RFP","N"],
		[2,"Collect Requirement","RFP","N"],
		[3,"Build Proposal Draft","Draft","N"],
		[4,"Draft Review","Draft","Y"],
		[5,"Archive Task","Archive","N"],
		[6,"PROPOSAL","PROD","N"],
		[7,"Upload Financial Doc","FIN","N"],
		[8,'Collect NDR','NDR','N']
	];