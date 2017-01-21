// this javascript handle UI for template editing

oms.admin.createTLPanel=function(){
	var panel=Ext.create('Ext.panel.Panel',{
	layout:'accordion',
	id:'tasktmpltadmin',
	title:'Task Templates',
	tbar:[
		{text:'New Task Template'}
	]
	});
	return panel; 
};

oms.admin.buildTLDetails=function(data){
	var panel=Ext.getCmp("tasktmpltadmin");
	panel.removeAll();
	for(var i=0;i<data.length;i++)
	{
	var child=oms.admin.createTTItemPanel(data[i]);
	panel.add(child);

	}
	panel.doLayout();
};
oms.admin.createTTItemPanel=function(task){
	function buildDocTypeHtml()
	{
		var res="<table width=100%>";
		res+='<tr><td width=15% ></td><td>RFP Draft</td><td width=100><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';
		res+='<tr><td width=15% ></td><td>NDA</td><td><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';
		res+='<tr><td width=15% ></td><td>RFP Final</td><td><img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" ></td></tr>';

		res+="<tr><td colspan=3><center><br><a href=''>Add New Doctype</a></center></td></tr>"
		res+="</table>";
		return res;
	}
	function buildSearchHtml()
	{
		return "to do";
	}
	var docpanel=Ext.create('Ext.panel.Panel',{ 
					title:'Required Documents:',
					margin:'5 5 5 5',
					frame:true,
					width:'45%',
					minHeight:200, 
					scrollable:true,
					html:buildDocTypeHtml()
					});
	var searchPanel=Ext.create('Ext.panel.Panel',{
					title:'Search Config:',
					margin:'5 5 5 5',
					width:'45%',
					minHeight:200,
					scrollable:true,
					frame:true,
					html:buildSearchHtml()
					});
	var panel=Ext.create('Ext.panel.Panel',{
					title:"["+task.name+"]", 
					width: '80%',
					layout: 'hbox',
					tools:[{type:'up'}],
					minHeight:320,
					frame:true,
					items:[
						docpanel,searchPanel
						]
						});
	return panel;
};

// project Template
oms.admin.createPLPanel=function(){
	var panel=Ext.create('Ext.panel.Panel',{
	layout:'accordion',
	id:'projtmpltadmin',
	title:'Projects Templates',
	tbar:[
		{text:'New Project Template'}
	]
	});
	return panel; 
};

oms.admin.buildPLDetails=function(data){
	var panel=Ext.getCmp("projtmpltadmin");
	panel.removeAll();
	for(var i=0;i<data.length;i++)
	{
	var child=oms.admin.createPTItemPanel(data[i]);
	panel.add(child);

	}
	panel.doLayout();
};
oms.admin.createPTItemPanel=function(task)
{
	
	function buildTaskListHtml()
	{
		var res="<table width=100%>";
		res+='<tr><td width=15% ></td><td>Task Template 12</td><td width=100><img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif" ></td></tr>';
		res+='<tr><td width=15% ></td><td>NDA DRAFT TASK</td><td><img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif" ></td></tr>';
		res+='<tr><td width=15% ></td><td>FINATIAL REVIEW TASK</td><td><img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif" ></td></tr>';
		res+="</table>";
		return res;
	}

	var docpanel=Ext.create('Ext.panel.Panel',{ 
					title:'',
					margin:'5 5 5 5',
					frame:true,
					width:'20%',
					minHeight:200, 
					scrollable:true,
					html:""
					});
	var searchPanel=Ext.create('Ext.panel.Panel',{
					title:'Task List:',
					margin:'5 5 5 5',
					width:'78%',
					minHeight:200,
					scrollable:true,
					frame:true,
					html:buildTaskListHtml()
					});
	var panel=Ext.create('Ext.panel.Panel',{
					title:"["+task.name+"]", 
					width: '98%',
					layout: 'hbox',
					tools:[{type:'up'}],
					minHeight:320,
					frame:true,
					items:[
						docpanel,searchPanel
						]
						});
	return panel;
};

