// this javascript handle UI for template editing

oms.admin.TTAddDTPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Add New DocType',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 420,
	mygrid:null,
	//modal: false,
	items:[{xtype:'form',	
		id:'ttAddDTform',
		width:'95%',
		frame:false,
		border:0,
		items:[
			{
				xtype:'combobox',
				labelWidth:120,
				fieldWidth:280,
				id:'dtcombo',
				valueField:'doctype',
				displayField:'doctype',
				queryMode: 'local',
	            typeAhead: true,
				fieldLabel:'Document Types',
				//store:{type:'dtStore'}
			}
		]
	}
	],
	buttons: [{
		text: 'Add',
		listeners:{
			click:
			{
				element:'el',
				fn:function(){
					var value=Ext.getCmp('dtcombo').getValue();
					if(value==null)
						{
							alert("No selected value");
						}
					else
					{
						//console.log(value);
						var grid=oms.admin.TTAddDTPanel.mygrid;
						var store=grid.getStore();
						
						store.add({doctype:value});
						//console.log(store.getData());
						//console.log(value);
						oms.admin.TTAddDTPanel.hide();
					}
					//oms.admin.TTAddDTPanel.hide();
				}
			}
	}
		
		}
		]
	});
		
oms.admin.TTEditPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Edit Task Template Info',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 480,
	MinHeight:220,
	//modal: false,

	items:[{xtype:'form',	
		id:'tteditform',
		width:'95%',
		defaultType: 'textfield',
		fieldDefaults: {
			labelAlign: 'right',
			labelWidth: 150,
			bodyPadding: 10,
			},items:[
	{ fieldLabel: 'Task Template ID', name: 'id'},
	{ fieldLabel: 'Template Name', name: 'name'},
	{ fieldLabel: 'Description', name: 'description'},
	{ fieldLabel: 'Status',name:'status'}]}
	],
	buttons: [{
		text: 'Save'
		}
		]
	});

oms.admin.createTLPanel=function(){
	var panel=Ext.create('Ext.panel.Panel',{
	layout:'accordion',
	id:'tasktmpltadmin',
	title:'Task Templates',
	tbar:[
		{
			text:'New Task Template',
			listeners:{
					click:
					{
						element:'el',
						fn:function(){

							oms.admin.TTEditPanel.show();
						}
					}
			}
		}
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
	
	var ustore=Ext.create('Ext.data.JsonStore', {
		// store configs
		//storeId: 'ttdtstore'+task.id,
		fields: [
			{name: 'doctype' }
			]
	});
	// now loaddata
	if(task.config)
	{
		//task.config ="{docs:[{doctype:'type1'},{doctype:'type2'},{doctype:'NDR'},{doctype:'RFP Draft'},{doctype:'RFP Final'}]}";
		var obj=Ext.JSON.decode(task.config);
		//console.log(obj);
			if(obj.docs)
			{
				ustore.setData(obj.docs);
			}
			
	}
	
	var grid=Ext.create('Ext.grid.Panel',{
		id:'ttdtgrid'+task.id,
		store:ustore, 
		scrollable:true,
		hideHeaders:true,
		columnLines:false,
		width:'90%',
	//	minHeight:220,
		columns: [
			{text: "Documents", dataIndex: 'doctype',width:'80%'}, 
			{
	            xtype:'actioncolumn',
	            width:30,
	            items: [
	            	{
	                icon: 'css/images/up.png'}],
	                handler: function(grid, rowIndex, colIndex) {    
	                	if(rowIndex>0)
	                	{
	                		var rec=grid.getStore().getAt(rowIndex);
	                		grid.getStore().removeAt(rowIndex);
	                		grid.getStore().insert(rowIndex-1,rec);
	                	}
	            }
			},
			{
	            xtype:'actioncolumn',
	            width:30,
	            items: [
	            	{
	            		icon: 'css/images/down.png'}], 
	            		handler: function(grid, rowIndex, colIndex)
	            		{    
	            			if(rowIndex<grid.getStore().data.length-1)
	                		{
	                		var rec=grid.getStore().getAt(rowIndex);
	                		grid.getStore().removeAt(rowIndex);
	                		grid.getStore().insert(rowIndex+1,rec);
	                		}
	            		}
	                
					},			
					{
						xtype:'actioncolumn',
						width:30,
						items: [{
							icon: 'css/images/shared/icons/fam/delete.gif'}], 
							handler: function(grid, rowIndex, colIndex) {
	                	
								grid.getStore().removeAt(rowIndex);
	                	
							}
					},			
			]
		});
	
	var docpanel=Ext.create('Ext.panel.Panel',{ 
					title:'Associated Documents:',
					margin:'5 5 5 5',
					frame:true,
					width:'68%',
					minHeight:160, 
					scrollable:true,
					items:grid,
					buttons:[						
						{
							text:'Add Document',
							listeners:
							{
								click:
								{	
									element:'el',
									fn:function()
									{
										var store=Ext.getStore('dtStore');
									//console.log(store);
										if(store!=null)
										{
											Ext.getCmp('dtcombo').setStore(store);
										}
										oms.admin.TTAddDTPanel.mygrid=grid;
										oms.admin.TTAddDTPanel.show();
									}
								}
							}
						},
						{text:"Save",
							listeners:
							{
								click:
								{	
									element:'el',
									fn:function()
									{
										var recs=grid.getStore().data.items;
										var res=[];
										for(var i=0;i<recs.length;i++)
										{
											 res[i]={doctype:recs[i].data.doctype};
										}
										console.log(res);
										Ext.Msg.alert("saving config data",Ext.encode(res));
									}
								}
							}	
						}
					]
					});
	var ttPanel=Ext.create('Ext.form.Panel',{
					title:'Task Template Info:',
					margin:'5 5 5 5',
					width:'30%',
					minHeight:160,
					scrollable:true,
					frame:true,
					defaultType:'displayfield',
					items:[
						{
							name:'id', 
							fieldLabel:'Template ID:', 
							margin: '0 2 0 15',
							value:task.id,
							labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
						},
						{
							name:'name', 
							fieldLabel:'Template Name:', 
							value:task.name,
							margin: '0 2 0 15',
							labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
						},
						{
							name:'description', 
							fieldLabel:'Description', 
							value:task.description,
							margin: '0 2 0 15',
							labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
						},
						{
							name:'status', 
							fieldLabel:'Status:', 
							value:task.status,
							margin: '0 2 0 15',
							labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
						}
					],
					buttons:[
						{
							text:'Edit Template Info',
							listeners:{
								click:{
									element:'el',
									fn:function(){
										//console.log(Ext.getCmp('tteditform').getForm());
										console.log(task);
										Ext.getCmp('tteditform').getForm().loadRecord({getData:function(){return task;}});
										oms.admin.TTEditPanel.show();
									}
								}
							}
						}
					]
					});
//	ttPanel.getForm().loadRecord({Data:task});
	var panel=Ext.create('Ext.panel.Panel',{
					title:"["+task.name+"]", 
					width: '80%',
					layout: 'hbox',
					tools:[{type:'up'}],
					minHeight:200,
					frame:true,
					items:[
						ttPanel,docpanel
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

