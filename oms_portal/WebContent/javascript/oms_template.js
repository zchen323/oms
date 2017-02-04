// this javascript handle UI for template editing

oms.admin.PTAddTTPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Assign Task',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 420,
	mygrid:null,
	//modal: false,
	items:[{xtype:'form',	
		id:'ptAddTTform',
		width:'95%',
		frame:false,
		border:0,
		items:[
			{
				xtype:'combobox',
				labelWidth:120,
				fieldWidth:280,
				id:'ttcombo',
				valueField:'id',
				displayField:'name',
				queryMode: 'local',
	            typeAhead: true,
				fieldLabel:'Task Templates',
				store:Ext.create('Ext.data.JsonStore', {fields: [{name: 'id' },{name: 'name'}]})
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
					
					var rec=Ext.getCmp('ttcombo').getSelection();
					//console.log(value);
					
					if(rec==null)
						{
							Ext.Msg.alert("Error","No selected value");
						}
					else
					{
						//console.log(value);
						var grid=oms.admin.PTAddTTPanel.mygrid;
						var store=grid.getStore();
						
						store.add(rec.data);
						//console.log(store.getData());
						//console.log(value);
						oms.admin.PTAddTTPanel.hide();
					}
					//oms.admin.TTAddDTPanel.hide();
					
				}
			}
			}
		
		}
		]
	});
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
						Ext.Msg.alert("Error","No selected value");
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
	{ fieldLabel: 'Task Template ID', name: 'id', xtype: 'hidden', readOnly: true},
	{ fieldLabel: 'Template Name', name: 'name'},
	{ fieldLabel: 'Description', name: 'description'},
	{ fieldLabel: 'Status',name:'status'}]}
	],
	buttons: [{
			text: 'Save',
			handler: function(){
				var formdata = Ext.getCmp('tteditform').getForm().getValues();
				console.log(formdata);
				Ext.Ajax.request({
					url : "api/projectadmin/taskTemplate",
					method : 'POST',
					jsonData : JSON.stringify(formdata),
					success : function(response, option) {
						console.log(response);
						var respObj = Ext.decode(response.responseText);
						Ext.Msg.alert(respObj.status, respObj.message);
						if (respObj.status === 'success') {
							oms.admin.TTEditPanel.hide();
						}
					},
					failure : function(response, option) {
						console.log(response);
						Ext.Msg.alert('Error', response.responseText);
					}
				});
				
				
			}
		}
		]
	});

oms.admin.PTEditPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Project Template Info',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 480,
	MinHeight:220,
	//modal: false,

	items:[{xtype:'form',	
		id:'pteditform',
		width:'95%',
		defaultType: 'textfield',
		fieldDefaults: {
			labelAlign: 'right',
			labelWidth: 150,
			bodyPadding: 10,
			},items:[
	{ fieldLabel: 'Project Template ID', name: 'id', xtype: 'hidden'},
	{ fieldLabel: 'Template Name', name: 'name'},
	{ fieldLabel: 'Description', name: 'description'},
	{ fieldLabel: 'Status',name:'status'}]}
	],
	buttons: [{
		text: 'Save',
		listeners:{
			click:{
				element:'el',
				fn:function(){
					
				}
			}
		},
		handler: function(){
			var formdata = Ext.getCmp('pteditform').getForm().getValues();
			console.log(formdata);
			Ext.Ajax.request({
				url : "api/projectadmin/projectTemplate",
				method : 'POST',
				jsonData : JSON.stringify(formdata),
				success : function(response, option) {
					console.log(response);
					var respObj = Ext.decode(response.responseText);
					Ext.Msg.alert(respObj.status, respObj.message);
					if (respObj.status === 'success') {
						oms.admin.PTEditPanel.hide();
					}
				},
				failure : function(response, option) {
					console.log(response);
					Ext.Msg.alert('Error', response.responseText);
				}
			});
			
		}
	}]
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
							Ext.getCmp('tteditform').getForm().reset();
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
		selType: 'checkboxmodel',
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
											//res[i]=recs[i].data.doctype;
										}
										//console.log(res);
										//console.log(task);
										var taskConfig = {};
										taskConfig.docs=res;
										//taskConfig.docTypes = res;
										//taskConfig.taskTemplateId = task.id;
										console.log(taskConfig);
										//Ext.Msg.alert("saving config data",Ext.encode(res));
										Ext.Ajax.request({
											url : "api/projectadmin/taskTemplate/config/" + task.id,
											method : 'POST',
											jsonData : JSON.stringify(taskConfig),
											success : function(response, option) {
												console.log(response);
												var respObj = Ext.decode(response.responseText);
												Ext.Msg.alert(respObj.status, respObj.message);
												if (respObj.status === 'success') {
													oms.admin.PTEditPanel.hide();
												}
											},
											failure : function(response, option) {
												console.log(response);
												Ext.Msg.alert('Error', response.responseText);
											}
										});
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
										//console.log(task);
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
					title:"Task Template:["+task.name+"]", 
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
		{
			text:'New Project Template',
			listeners:{
				click:{
					element:'el',
					fn:function()
					{
						Ext.getCmp('pteditform').getForm().reset();
						oms.admin.PTEditPanel.show();
					}
				}
			}
		}
	]
	});
	return panel; 
};

oms.admin.buildPLDetails=function(data){
	
	//console.log("====buidPLDetails ===")
	//console.log(data);
	
	var panel=Ext.getCmp("projtmpltadmin");
	panel.removeAll();
	for(var i=0;i<data.length;i++)
	{
	var child=oms.admin.createPTItemPanel(data[i]);
	panel.add(child);

	}
	panel.doLayout();
};
oms.admin.createPTItemPanel=function(proj)
{
	//console.log("=====createPTItemPanel=======");
	//console.log(proj);

	var ustore=Ext.create('Ext.data.JsonStore', {
		// store configs
		//storeId: 'ttdtstore'+task.id,
		fields: [
			{name: 'id' },
			{name: 'name'}
			]
	});
	// now loaddata
	//proj.config="{tasks:[{id:1,name:'task 1'},{id:2,name:'task 2'}]}";
	if(proj.config)
	{
		//task.config ="{docs:[{doctype:'type1'},{doctype:'type2'},{doctype:'NDR'},{doctype:'RFP Draft'},{doctype:'RFP Final'}]}";
		
		var obj=Ext.JSON.decode(proj.config);
		//console.log(obj);
			if(obj.tasks)
			{
				ustore.setData(obj.tasks);
			}
			
	}
	
	var grid=Ext.create('Ext.grid.Panel',{
		id:'ptdtgrid'+proj.id,
		store:ustore, 
		scrollable:true,
		hideHeaders:true,
		columnLines:false,
		width:'90%',
	//	minHeight:220,
		columns: [
			{text:'ID',dataIndex:'id',width:'10%'},
			{text: "Task Name", dataIndex: 'name',width:'70%'}, 
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
	
	var ptPanel=Ext.create('Ext.form.Panel',{
		title:'Project Template Info:',
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
				value:proj.id,
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'name', 
				fieldLabel:'Template Name:', 
				value:proj.name,
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'description', 
				fieldLabel:'Description', 
				value:proj.description,
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'status', 
				fieldLabel:'Status:', 
				value:proj.status,
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
							console.log(proj);
							Ext.getCmp('pteditform').getForm().loadRecord({getData:function(){return proj;}});
							oms.admin.PTEditPanel.show();
						}
					}
				}
			}
		]
		});
	
	var tlPanel=Ext.create('Ext.panel.Panel',{
					title:'Task List:',
					margin:'5 5 5 5',
					width:'68%',
					minHeight:200,
					scrollable:true,
					frame:true,
					items:grid,
					buttons:[						
						{
							text:'Add Task',
							listeners:
							{
								click:
								{	
									element:'el',
									fn:function()
									{
										var store=Ext.getCmp('ttcombo').getStore();
										store.setData(oms.admin.cachedata.taskTemplates);
										console.log("== taskTemplates");
										console.log(oms.admin.cachedata.taskTemplates);
										oms.admin.PTAddTTPanel.mygrid=grid;
										oms.admin.PTAddTTPanel.show();
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
											//console.log(recs[i]);
											res[i]=recs[i].data;
										}
										console.log("====Saving Config data");
										console.log(proj);
										
										//Ext.Msg.alert("Saving Config data",Ext.encode({tasks:res}));
										Ext.Ajax.request({
											url : "api/projectadmin/projectTemplate/config/" + proj.id,
											method : 'POST',
											jsonData : JSON.stringify({tasks:res}),
											success : function(response, option) {
												console.log(response);
												var respObj = Ext.decode(response.responseText);
												Ext.Msg.alert(respObj.status, respObj.message);
											},
											failure : function(response, option) {
												console.log(response);
												Ext.Msg.alert('Error', response.responseText);
											}
										});
										
									}
								}
							}	
						}
					]
					});
	
			var panel=Ext.create('Ext.panel.Panel',{
					title:"Project Template: ["+proj.name+"]", 
					width: '98%',
					layout: 'hbox',
					tools:[{type:'up'}],
					minHeight:320,
					frame:true,
					items:[
						ptPanel,tlPanel
						]
						}
	
			);
	return panel;
};

