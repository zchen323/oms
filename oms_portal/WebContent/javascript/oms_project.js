
oms.project={}; // project builder
// this will return a fieldSets
// open project model and store

oms.project.openProjectPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Searching Project',
	bodyPadding: 10,
	width:420,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	height:180,
	items:[{
			xtype:'displayfield',			
			margin: '0 2 2 15',
			value:'Please enter project name'
		},
		{
			xtype:'combobox',
			width:'90%',
			margin: '0 2 2 15',
			id:'projsearchkey',
			valueField:'projId',
			displayField:'projName',
			minChars: 1,
            queryParam: 'pname',
            queryMode: 'remote',
            typeAhead: true,
            multiSelect: false,
            triggerAction: 'all',
            allowBlank:false,
            store:Ext.create('Ext.data.JsonStore', {
				fields: [
					{name: 'projId' },
					{name: 'projName'}
					],
				proxy: {
				        type: 'ajax',
				        url: 'api/project/search',
				        reader: {
				            type: 'json',
				            rootProperty: 'result'
				        }
				    }

			}),
			tpl: Ext.create('Ext.XTemplate','<tpl for=".">',
					 	'<div class="x-boundlist-item" style="border-bottom:1px solid #f0f0f0;">',
				      '<div>[Project ID: {projId} ] --<font size=+1 color=green>{projName}</font></div></div>',
				       '</tpl>'
					),
			displayTpl:Ext.create('Ext.XTemplate','<tpl for=".">',
				      '[ID:{projId} ] --{projName}',
				       '</tpl>'
					)
		}
	],
	buttons:[
		{
			text:"Open Project",
			handler: function(){
				var rec=Ext.getCmp('projsearchkey').getSelection();
				console.log(rec.data);
				oms.project.openProjectPanel.hide();
				Ext.Ajax.request({
					url:'api/project/'+rec.data.projId,
					success:function(response)
					{
						var obj=Ext.JSON.decode(response.responseText);
						var proj=obj.result;
					//	console.log(obj.result);
						var ppanelID="projectPanel"+proj.projectInfo.projId;
						if(Ext.getCmp(ppanelID)!=null)
						{
								// panel already exist
							  Ext.getCmp('centerViewPort').setActiveTab(Ext.getCmp(ppanelID));
						}
						else
						{
							var p_proj=oms.project.createProjectPanel(proj);
							Ext.getCmp('centerViewPort').add(p_proj);
							Ext.getCmp('centerViewPort').setActiveTab(p_proj);
						}
					},
					failure:function(response)
					{
						console.log(response);
					}
				});
			}
		}
		]
});
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
		projectInfo:pinfo,
		header:{
			baseCls:'omspanelheadercls',
			items:[{xtype:'label',html:'<span style="font-size:10pt"><b>Project Info</b></span>',width:'85%'}]
},
	tools:[
		{
			type:'gear',
			handler:function()
			{
				oms.project.editProjInfoPanel.show();
				Ext.getCmp('editprojinfopanel').getForm().loadRecord({
					getData:function(){return pinfo;}
				});
			}
			
		},
		{type:'refresh'} 
		],
		//buttonAlign:'left',
		defaultType:'displayfield',
		layout:'vbox', 
		bodypadding:'10 10 10 10',

		items:[
			{
				name:'projName', 
				fieldLabel:'Project Name:', 
				value:"", 
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projManager', 
				fieldLabel:'Project Manager:', 
				value:"", 
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projStatus', 
				fieldLabel:'Project Status:', 
				value:"", 
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projAgency', 
				fieldLabel:'Project Agency:', 
				value:"", 
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projOrg', 
				fieldLabel:"Organization:",
				value:"",
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projSubOrg', 
				fieldLabel:'Sub Org.:',
				value:'Sub Org',
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'

			},
			{ 
				name:'projloc',
				fieldLabel:'Project Location',
				value:"",
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle' 
			},
			{
				name:'contactoffice', 
				fieldLabel:'Contact Office',
				value:"",
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
				value:'',
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'primeName',
				fieldLabel:'Prime Name:',
				value:"",
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{
				name:'projduedate',

				fieldLabel:'Target Date', 
				margin: '0 2 0 15',
				value:'',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'

			}, 
			{ 
				name:'projstartdate',
				fieldLabel:'Start Date', 
				value:"",
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			},
			{ 
				name:'projcompletedate',
				fieldLabel:'Complete Date', 

				value:'',
				margin: '0 2 0 15',
				labelCls:'omslabelstyle',
				fieldCls:'omsfieldstyle'
			} 
			]
	});
	
	panel.getForm().loadRecord({
				getData:function(){return pinfo;}
			});
	return panel;
};

oms.project.createTaskItemPanel=function(task,seq,porj)
{
	// build header
	seq=seq+1;
	var h_html='<table width=100% style="background-color:#eeeeff;font-size:8pt"><tr>';
	h_html=h_html+"<td width=30>#"+seq+"</td>";
	h_html=h_html+"<td><font color=green>"+task.name+"</font></td>";
	h_html=h_html+"<td width=15%><font color=#336699><div id='task_ownerlbl_"+task.id+"'>"+task.owner+"</div></font></td>"; 
	h_html=h_html+"<td width=15%><font color=green><div id='task_statuslbl_"+task.id+"'>"+task.status+"</div></font></td>";
	h_html=h_html+"<td width=20%><font color=green><div id='task_tgtdtlbl_"+task.id+"'>"+task.targetDate+"</div></font></td>";
	h_html=h_html+'<td width=10%><a href="" onclick="oms.task.showTaskInfoEdit('+task.id+');return false;"><img src="css/images/shared/icons/fam/cog_edit.png"></a></td>';
	h_html=h_html+"</tr></table>";
	var taskid=task.id;
	// now build commend and document panel
	var clgrid=oms.task.createTaskCommentPanel(task,taskid);
	var dlgrid=oms.task.createTaskDocumentPanel(task,taskid); 
	//console.log(clgrid);
	var taskpanel=Ext.create('Ext.panel.Panel',{
		id:"taskitem_"+taskid,
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
				width:'96%',
				// tabPosition: 'left',
				margin:'2 2 2 5', 
				flex:1, 
				defaults:{
					bodypadding:3,
					scrollable:true,
					border:true
				}, 
				items:[
					dlgrid,
					clgrid,
					{
						title:'Related Content',
						html:oms.ui.getSearchHtml()
					} 
					]
				}
			]
	});
	taskpanel.task=task;
	return taskpanel;
};

oms.project.createTaskListPanel=function(taskList,projID)
{
	var pc=Ext.create('Ext.panel.Panel',{
		id:'tasklistpanel'+projID, 
		title:'Tasks List', 
		items:[]
	}); 

	var data=taskList;
	//console.log(data);
	for(var i=0;i<data.length;i++)
	{
		console.log(data[i]);
		var itempanel=oms.project.createTaskItemPanel(data[i],i);
		if(data[i][4]=='in progress')
		{
		itempanel.collapsed=false;
		}
		pc.add(itempanel);
	}
	//pc.doLayout();

	return pc;
};

oms.project.createDocumentPanel=function(proj,projID)
{
	var dlstore=Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId: 'dlStore'+projID,
		fields: [
			{name: 'docid'}, 
			{name: 'docname'},
			{name: 'doctype'}, 
			{name: 'taskname' },
			{name: 'user'},
			{name: 'uploadDate', type: 'date'},
			{name: 'required'},
			{name: 'restricted',type:'boolean'}
			],
	});
	// append all docs to a bigger list
	//console.log(proj);
	var tasklist=proj.tasks;
	var pdocs=[];
	if(tasklist!=null)
	{
		for(var i=0;i<tasklist.length;i++)
		{
			var docs=tasklist[i].docs;
			if(docs!=null)
			{
				for(var j=0;j<docs.length;j++)
				{
					docs[j].taskname=tasklist[i].name;
				//	console.log(docs[j]);
					pdocs[pdocs.length]=docs[j];
				}
				//pdocs.concat(docs);
			}
		}
	}

	dlstore.setData(pdocs);
	var grid=Ext.create('Ext.grid.Panel',{
		id:'projdocgrid'+projID,
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
			 {text: "Name", flex:2,dataIndex: 'name',
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
			{text: "Task", flex:1,dataIndex: 'taskname'},
			{text: "Upload Date", dataIndex: 'uploadDate',formatter: 'date("m/d/Y")'},
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
            { 
         	   xtype:'actioncolumn',
		           width:100,
		           items: [
		        	   {
		        		   icon: 'css/images/shared/icons/fam/information.png',
		        	       handler: function(grid, rowIndex, colIndex) {
			                    var rec = grid.getStore().getAt(rowIndex);
			                    console.log(rec);
			                    
			                    if(Ext.getCmp('docPanel'+rec.data.documentId)!=null)
			                    	{
			                    		Ext.getCmp('centerViewPort').setActiveTab(Ext.getCmp('docPanel'+rec.data.documentId));
			                    	}
			                    else{
			                    	// now need to ajax calls
				                    	Ext.Ajax.request
				                    	({
				                    		url:'api/document/'+rec.data.documentId,
				                    		success:function(response)
				                    		{
				                    			var obj=Ext.JSON.decode(response.responseText);
				                    			console.log(obj);
				                    			var doc=obj.result;
				                    			var dpanel=oms.doc.createDocMainPanel(doc);
				                    			Ext.getCmp('centerViewPort').add(dpanel);
				                    			Ext.getCmp('centerViewPort').setActiveTab(dpanel);
				                    		},
				                    		failure: function(response) 
				                    		        { 
				                    		            console.log(response.responseText); 
				                    		        } 
	
				                    	 });

			                    		}
		        	       	}
		        	   	}]
            }
		],
		columnLines: true, 
		title:' Documents ' 
	});
	return grid;
};


oms.project.createUserPanel=function(userlist,projID)
{
	var ulstore=Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId: 'ulStore'+projID,
		fields: [
		{name: 'userid'}, 
		{name: 'username'},
		{name: 'projectId'},
		{name:'projectUserRole'},
		{name: 'restricted',type:'boolean'}
		],
		data:userlist
	});
	var grid=Ext.create('Ext.grid.Panel',{
		id:'usergrid'+projID,
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
			{text: "Role", flex:1, dataIndex: 'projectUserRole'}, 
			{ 	xtype:'actioncolumn',
	            width:30,
	            items: [
	            	{
	            		icon: 'css/images/shared/icons/fam/delete.gif', 
	            		handler: function(grid, rowIndex, colIndex)
	            		{    
	    					Ext.getCmp('btnaddprojuser').hide();
	    					Ext.getCmp('btnremoveprojuser').show();
	    					Ext.getCmp('idprojectUserRole').getStore().setData(oms.admin.cachedata.projectUserRoleTypes)
	    					Ext.getCmp('idprojuserprojID').setValue(projID);
	    					var rec=grid.getStore().getAt(rowIndex);
	    					console.log(rec);
	    					Ext.getCmp('projusereditform').getForm().loadRecord(rec);
	    					oms.project.AssignNewUserPanel.show();
	            		}	                
					}
				]
			}
		],
		columnLines: true, 
		title:' Team Members ',
		
		tbar: [
			{ 
				text:'Add User',
				handler: function(){
					Ext.getCmp('btnaddprojuser').show();
					Ext.getCmp('btnremoveprojuser').hide();
					Ext.getCmp('idprojectUserRole').getStore().setData(oms.admin.cachedata.projectUserRoleTypes)
					Ext.getCmp('idprojuserprojID').setValue(projID);
					oms.project.AssignNewUserPanel.show();
				}
			} 
		]
		});
	return grid;
};


oms.project.AssignNewUserPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Project User Assignment',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 500,
	MinHeight:220,
	//modal: false,

	items:[
				{
					xtype:'form',	
				id:'projusereditform',
				width:'99%',
				defaultType: 'textfield',
				fieldDefaults: {
					labelAlign: 'right',
					labelWidth: 150,
					bodyPadding: 10,
				},
				items:[
						{
							xtype:'textfield',
							name:'projectId', 
							id:'idprojuserprojID',
							fieldLabel:'Project ID:', 
							margin: '0 2 5 15',
							labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
						},
						{
							xtype:'combobox',
							margin: '0 2 2 15',
							id:'projusersearchkey',
							valueField:'username',
							displayField:'name',
							name:"userId",
							minChars: 1,
				            queryParam: 'uname',
				            queryMode: 'remote',
				            typeAhead: true,
				            multiSelect: false,
				            triggerAction: 'all',
				            fieldLabel:'Enter User Name',
				            labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle',
				            allowBlank:false,
				            store:Ext.create('Ext.data.JsonStore', {
								fields: [
									{name: 'username' },
									{name: 'name'}
									],
								proxy: {
								        type: 'ajax',
								        url: 'api/user/search',
								        reader: {
								            type: 'json',
								            rootProperty: 'result'
								        }
								    }

							})
						},
						{
							xtype:'combobox',
							margin: '0 2 5 15',
							id:'idprojectUserRole',
							name:'projectUserRole',
							valueField:'roletype',
							displayField:'roletype',
							queryMode: 'local',
		            		typeAhead: true,
		        			store:Ext.create('Ext.data.JsonStore', {
								fields: [
									{name: 'roletype' }
									]
							}),
				            labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle',
							fieldLabel:'Role'
						},	
						{
							xtype:'checkbox',
							margin: '0 2 5 15',
							name:'restricted',
							valueField:'Restricted',
				            labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle',
							displayField:'Restricted',
							fieldLabel:'Restricted Access',
							value:0,
							inputValue:true,
							uncheckValue:false
						}
					],
					buttons: 
						[
								{
									text: 'Add Project User',
									id:'btnaddprojuser',
									handler: function(){
										console.log("add project user");
										var formData = Ext.getCmp("projusereditform").getForm().getValues();
										console.log(formData);
										Ext.Ajax.request({
											url : "api/project/user",
											method : 'POST',
											jsonData : JSON.stringify(formData),
											success : function(response, option) {
												console.log(response);
												var respObj = Ext.decode(response.responseText);
												Ext.Msg.alert(respObj.status, respObj.message);
												console.log(respObj);
												if (respObj.status === 'success') {
													oms.project.AssignNewUserPanel.hide();
													var grid=Ext.getCmp("usergrid"+formData.projectId)
													grid.getStore().setData(respObj.result);
												}
											},
											failure : function(response, option) {
												console.log(response);
												Ext.Msg.alert('Error', response.responseText);
											}
										});
									}
								},
								{
									text: 'Remove Project User',
									id:'btnremoveprojuser',
									handler: function(){
										// delete from project user
										var formData = Ext.getCmp("projusereditform").getForm().getValues();
										console.log("remove user from project")
										console.log(formData);
										
										Ext.Ajax.request({
											url : "api/project/user?projectId=" + formData.projectId + "&userId=" + formData.userId + "&projectUserRole=" + formData.projectUserRole,
											method : 'DELETE',
											//jsonData : JSON.stringify(formData),
											success : function(response, option) {
												console.log(response);
												var respObj = Ext.decode(response.responseText);
												Ext.Msg.alert(respObj.status, respObj.message);
												if (respObj.status === 'success') {
													oms.project.AssignNewUserPanel.hide();
													var grid=Ext.getCmp("usergrid"+formData.projectId)
													grid.getStore().setData(respObj.result);
												}
											},
											failure : function(response, option) {
												console.log(response);
												Ext.Msg.alert('Error', response.responseText);
											}
										});
										
									}
								},
						]
			}]
});

oms.project.editProjInfoPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Edit Project Info',
	bodyPadding: 10,
	width:420,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	items:[
		{
			xtype:'form',
			id: 'editprojinfopanel',
			border:0,
			defaultType:'textfield',
			layout:'vbox', 
			bodypadding:'10 10 10 10',
			items:[
				{
					name:'projId', 
					fieldLabel:'Project ID:', 
					value:"", 
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'projName', 
					fieldLabel:'Project Name:', 
					value:"", 
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'projManager', 
					xtype:'combobox',
					id:'projmgrcombo',
					valueField:'userId',
					displayField:'username',
					queryMode: 'local',
		            typeAhead: true,
					store:Ext.create('Ext.data.JsonStore', {fields: [{name: 'userId' },{name:'username'}]}),
					fieldLabel:'Project Manager:', 
					margin: '0 2 5 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					xtype:'combobox',
					name:'projStatus', 
					id:'projstatuscombo',
					valueField:'status',
					displayField:'status',
					queryMode: 'local',
		            typeAhead: true,
		    		fieldLabel:'Project Status:',
					margin: '0 2 5 15',
					store:Ext.create('Ext.data.ArrayStore', {fields: [{name: 'status' }]}),
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'projAgency', 
					fieldLabel:'Project Agency:', 
					value:"", 
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					xtype:'combobox',
					name:'projorg', 
					id:'projorgcombo',
					valueField:'org',
					displayField:'org',
					queryMode: 'local',
		            typeAhead: true,
		    		fieldLabel:'Organization:',
					margin: '0 2 5 15',
					store:Ext.create('Ext.data.ArrayStore', {fields: [{name: 'org' }]}),
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'projSubOrg', 
					fieldLabel:'Sub Org.:',
					value:'Sub Org',
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'

				},
				{ 
					name:'projloc',
					fieldLabel:'Project Location',
					value:"",
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle' 
				},
				{
					name:'contactoffice', 
					fieldLabel:'Contact Office',
					value:"",
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'

				},
				{
					xtype:'combobox',
					name:'projcategory', 
					id:'projcategorycombo',
					valueField:'cate',
					displayField:'cate',
					queryMode: 'local',
		            typeAhead: true,
		    		fieldLabel:'Category:',
					margin: '0 2 5 15',
					store:Ext.create('Ext.data.ArrayStore', {fields: [{name: 'cate' }]}),
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'isPrimeProject',
					fieldLabel:'Through Prime',
					value:'',
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'primeName',
					fieldLabel:'Prime Name:',
					value:"",
					margin: '0 2 3 15',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'
				},
				{
					name:'projduedate',
					xtype:'datefield',
					fieldLabel:'Target Date', 
					margin: '0 2 3 15',
					value:'',
					labelCls:'omslabelstyle',
					fieldCls:'omsfieldstyle'

				}
				],
	buttons:[
		{
			text:"Update Project",
			handler: function(){		
			}
		}]}]
	
});


oms.project.projectUserUpdate=function(users)
{
	Ext.getCmp('taskownercombo').getStore().setData(users);
	Ext.getCmp('projmgrcombo').getStore().setData(users);
};
oms.project.createProjectPanel=function(proj) // proj is the json data for the project
{
	var sample=oms.project.sample1;
	var infop=oms.project.createProjInfoPanel(proj.projectInfo) ;
	var tlgrid=oms.project.createTaskListPanel(proj.tasks,proj.projectInfo.projId);
	var dlgrid=oms.project.createDocumentPanel(proj,proj.projectInfo.projId);
	var ulgrid=oms.project.createUserPanel(proj.projectUsers,proj.projectInfo.projId);
	//console.log(infop);
	// update projectuser
	oms.project.projectUserUpdate(proj.projectUsers);
	var mainpanel=Ext.create('Ext.panel.Panel',{
		id:"projectPanel"+proj.projectInfo.projId,
		project:proj,
		layout:'hbox',
		title:"[PROJECT"+proj.projectInfo.projId+"]: -- ["+proj.projectInfo.projName+"]",
		padding:'5 5 5 5',
		border:true,
		items:[
				{
							xtype:'tabpanel',
							id:'projectDetails'+proj.projectInfo.projId,
							width:'99%',
							minHeight:400,
							margin:'2 2 2 2',
							flex:1, 
							defaults:
							{
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
				},
				{
					xtype:'container',
					width:'30%',
					layout:'vbox',
					items:[
						infop,
						oms.project.createProjLastUpdate()
					]
				}]
		});
	return mainpanel;
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  test mockup sample data
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
oms.project.statuslist=[['New'],['In Progress'],['Completed'],['Withdraw'],['Pending']];
oms.project.orglist=[['DOD'],['Army'],['DOE'],['Bexar Country'],['DOT']];
oms.project.categorylist=[['IT'],['ERP'],['Upgrade'],['Engineering'],['Security']];
Ext.getCmp('projstatuscombo').getStore().setData(oms.project.statuslist);
Ext.getCmp('projorgcombo').getStore().setData(oms.project.orglist);
Ext.getCmp('projcategorycombo').getStore().setData(oms.project.categorylist);