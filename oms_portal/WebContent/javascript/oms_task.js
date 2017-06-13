oms.task={};   // project builder
// this will return a fieldSets
oms.task.createTaskInfoPanel=function(tinfo) // json object of the project info
{
	var panel=Ext.create('Ext.form.Panel', {
	//	title:"Project Information:",
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

oms.task.createTaskCommentPanel=function(task,id)
{
	// first load the store
	var clstore=Ext.create('Ext.data.JsonStore', {
	    // store configs
	    storeId: 'clStore'+id,
	    fields: [
	             {name: 'id'},
	             {name: 'user' },	             
	             {name: 'date', type: 'date'},
	             {name: 'title'},
	             {name: 'content'}
	         ],
	    
	});
	clstore.setData(task.notes);
	var grid=Ext.create('Ext.grid.Panel',{
		id:'commentgrid'+id,
		store:clstore,		
		scrollable:true,
		tbar:[
		      {		text:"Add Comment",
		 			handler:function()
		 			{
		 				Ext.getCmp('addtaskcommentpanel').getForm().loadRecord({getData:function(){return task;}});
		 				oms.task.addCommentPanel.show();
		 			} 
		 		
		 }
		      ],
	    columns: [
	              {text: "#",  dataIndex: 'id'},
	              {text: "Title",flex:1, dataIndex: 'title'},
	              {text: "User", flex:2, dataIndex: 'user',width:160},
	              {text: "Date", dataIndex: 'date',formatter: 'date("m/d/Y")'},
	              {
	            	  text:"",dataIndex:"id",
	            	  renderer:function(val)
		               {
		            	   var html='<img src="css/images/shared/icons/fam/user_edit.png">';
		            	   return html;
		            		 
		               } 
	              }
	          ],
	     columnLines: true,    
	     title:'Notes',
	     plugins: [{
	         ptype: 'rowexpander',
	         rowBodyTpl : new Ext.XTemplate(
	        	
	             '<font color=#336699><h3>Content</h3><p></font><hr>',	             
	             '<font color=green>{content}</font>'
	         )
	     }],
	});
	return grid;
};
oms.task.openLoadDocuemnt=function(id,rowindex)
{
	//	Ext.getCmp('addtaskdocumentpanel').getForm().loadRecord({getData:function(){return task;}});
			var store=Ext.getStore('dtStore');
		//console.log(store);
			if(store!=null)
			{
				Ext.getCmp('dtcombo1').setStore(store);
			}
			var store=Ext.getStore( 'taskdlStore'+id);
			var rec=store.getAt(rowindex);
			console.log(rec);
			rec.data.taskdocid=rec.data.id
			Ext.getCmp('addtaskdocumentpanel').getForm().reset();
			Ext.getCmp('addtaskdocumentpanel').getForm().loadRecord(rec);
			Ext.getCmp('addtaskdocumentpanel').getForm().loadRecord({getData:function(){return {"id":id};}});
			oms.task.addDocumentPanel.show();
};
oms.task.createTaskDocumentPanel=function(task,id)
{
	var dlstore=Ext.create('Ext.data.JsonStore', {
	    // store configs
	    storeId: 'taskdlStore'+id,
	    fields: [
	    		 {name:'id'},
	             {name: 'seq'},	
	             {name: 'docid'},
	             {name: 'name'},
	             {name: 'doctype'},	             	        
	             {name: 'user'},
	             {name: 'uploadDate', type: 'date'},
	             {name: 'required'},
	             {name: 'restricted',type:'boolean'}
	         ],
	});
			console.log("create docs for task"+id);
			console.log(task.docs);
			dlstore.setData(task.docs);

	var grid=Ext.create('Ext.grid.Panel',{
		id:'taskdoclistgrid'+id,
		store:dlstore,		
		width:'98%',
		scrollable:true,
		tbar:[
		      {
		    	text:"Add New Document",
		 		handler:function()
		 		{
		 			Ext.getCmp('addtaskdocumentpanel').getForm().reset();
		 			Ext.getCmp('addtaskdocumentpanel').getForm().loadRecord({getData:function(){return task;}});
		 			var store=Ext.getStore('dtStore');
					//console.log(store);
						if(store!=null)
						{
							Ext.getCmp('dtcombo1').setStore(store);
						}
		 			oms.task.addDocumentPanel.show();
		 		} 
		      }],
		
	    columns: [
	              {text:"",dataIndex:'restricted', width:20,
	               renderer:function(val)
	               {
	            	 if(val==true)
	            	 {
	            		 return "<img src='css/images/sec.jpg' width=12 />"; 
	            	 }
	            	 return "";
	               }	            	  
	              },
	              {text: "Name",flex:1,dataIndex: 'name',
	               renderer:function(val, meta, rec, rowIdx)
	               {
	            	   if(val==null)
	            		 {
	            		   return "<a href='#' onclick='oms.task.openLoadDocuemnt("+id+","+rowIdx+");return false;'>Upload Now</a>";
	            		 }
	            	   else{
	            		   return val;
	            	   }
	      
	               }
	              },
	              {text: "type",  dataIndex: 'doctype',width:120},	         
	              {text: "Date", dataIndex: 'uploadDate',formatter: 'date("m/d/Y")',width:80},
	              {text: "User",  dataIndex: 'user',width:80},	
	              {dataIndex:"required",width:24,
	            	renderer:function(val)
	            	{
	            		if(val=="Y")
	            			{
	            			  return '<font color=red>'+val+'</font>';
	            			}
	            		return val;
	            	}
	               },
	              { 
	            	   xtype:'actioncolumn',
			           width:40,
			           items: [			        	   
			        	   {
			        		   icon: 'css/images/shared/icons/fam/delete.gif',
			        		   handler: function(grid, rowIndex, colIndex) {
				                    var rec = grid.getStore().getAt(rowIndex);
				                    //console.log(rec);
				                    var myMask = Ext.MessageBox.wait("Remove Document and Reloading task...");
				                    Ext.Ajax.request({
				                    	url: 'api/document/delete/' + rec.data.documentId,
										success : function(response, option) {
											console.log(response);
											var respObj = Ext.decode(response.responseText);
											//Ext.Msg.alert(respObj.status, respObj.message);
											if (respObj.status === 'success') {
												
												Ext.Ajax.request({
													url : "api/project/task/"+id+"/doc",
													method : 'GET',
														success : function(response, option) {
															var respObj = Ext.decode(response.responseText);
															if (respObj.status === 'success') {
																Ext.getStore('taskdlStore'+id).setData(respObj.result);
															}
															myMask.close();
														},
														failure : function(response, option) {
															console.log(response);
															Ext.Msg.alert('Error', response.responseText);
														}
													
												});
											}else{
												Ext.Msg.alert('Error', respObj.message);
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
				                    	Ext.Ajax.request({
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
			        	   }       
			           ]
	              }
	          ],
	     columnLines: true,    
	     title:' Documents '	    
	});
	console.log("document done");
	return grid;
};

oms.task.addCommentPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Add New Comment',
	bodyPadding: 10,
	width:380,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	items:[
			{
				xtype:'form',
				id: 'addtaskcommentpanel',
				border:0,
				defaultType:'textfield',
				layout:'vbox', 
				width:'95%',
				bodypadding:'10 10 10 10',
				items:[
					{
						xtype:'textfield',
						name:'id', 
						fieldLabel:'Task ID:', 
						margin: '0 2 5 15',
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle'
					},
					{
						xtype:'textfield',
						name:'ctitle', 
						fieldLabel:'Title', 
						margin: '0 2 5 15',
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle'
					},
					{
			            xtype: 'textareafield',
			            fieldLabel:'Content',
			            name:'content',
			           // hideLabel: true,
			            margin: '0 2 5 15',
			            	labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
			        }
				],
				buttons:[
					{
						text:"Save",
						handler: function(){
							var formdata = Ext.getCmp("addtaskcommentpanel").getForm().getValues();
							console.log(formdata);
							//Object {id: "8", ctitle: "qwewqe", content: "qwewqeqwe"}
							// change to {taskId: "8", title "title". content: "content"}
							formdata.taskId = formdata.id;
							formdata.title = formdata.ctitle;
							delete formdata.id;
							delete formdata.ctitle;
							
				
							console.log(formdata);
							
							Ext.Ajax.request({
								url : "api/project/task/comment",
								method : 'POST',
								jsonData : JSON.stringify(formdata),
								success : function(response, option) {
									console.log(response);
									var respObj = Ext.decode(response.responseText);
									Ext.Msg.alert(respObj.status, respObj.message);
									if (respObj.status === 'success') {
										var notes=respObj.result;
										Ext.getCmp('commentgrid'+formdata.taskId).getStore().setData(notes);
										oms.task.addCommentPanel.hide();
									}
								},
								failure : function(response, option) {
									console.log(response);
									Ext.Msg.alert('Error', response.responseText);
								}
							});
						}
					}]
			}]
});
oms.task.addDocumentPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Add New Document',
	bodyPadding: 10,
	width:420,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	items:[
			{
				xtype:'form',
				id: 'addtaskdocumentpanel',
				border:0,
				defaultType:'textfield',
				layout:'vbox', 
				width:'95%',
				bodypadding:'10 10 10 10',
				items:[
					{
						xtype:'hiddenfield',
						name:'taskdocid', 
						fieldLabel:'Task Document ID:', 
						margin: '0 2 5 15',
						editable:false,
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle'
					},
					{
						xtype:'hiddenfield',
						name:'id', 
						fieldLabel:'Task ID:', 
						margin: '0 2 5 15',
						editable:false,
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle'
					},
					{
						xtype:'hiddenfield',
						name:'projectId'
					},
					{
						xtype:'textfield',
						name:'docname', 
						fieldLabel:'Document Name', 
						margin: '0 2 5 15',
						labelCls:'omslabelstyle',
						fieldCls:'omsfieldstyle'
					},
					{
						xtype:'combobox',
						margin: '0 2 5 15',
						id:'dtcombo1',
						name:'doctype',
						valueField:'doctype',
						displayField:'doctype',
						queryMode: 'local',
	            		typeAhead: true,
						fieldLabel:'Document Types',
				
					},
					{
						xtype:'checkbox',
						margin: '0 2 5 15',
						name:'restricted',
						valueField:'restricted',
						fieldLabel:'Restricted' 
					},
					{
			            xtype: 'filefield',
			            fieldLabel:'File',
			            name: 'file',
			           // hideLabel: true,
			            reference: 'basicFile',
			            margin: '0 2 5 15',
			            	labelCls:'omslabelstyle',
							fieldCls:'omsfieldstyle'
			        }
				],
				buttons:[
					{
						text:"Save",
						handler: function(){
							var form = Ext.getCmp("addtaskdocumentpanel").getForm();
							var taskID=form.getValues().id;
							//console.log(form);
							form.submit({
								url: 'api/document/upload',
								waitMsg: 'Uploading file...',
								success : function(response, option) {
									//console.log(response);
									//var respObj = Ext.decode(response.responseText);
									Ext.Msg.alert("Success", option.result.file);
									//if (respObj.status === 'success') {
									Ext.Ajax.request({
										url : "api/project/task/"+taskID+"/doc",
										method : 'GET',
											success : function(response, option) {
												var respObj = Ext.decode(response.responseText);
												if (respObj.status === 'success') {
													Ext.getStore('taskdlStore'+taskID).setData(respObj.result);
												}
											},
											failure : function(response, option) {
												console.log(response);
												Ext.Msg.alert('Error', response.responseText);
											}
										
									});
										oms.task.addDocumentPanel.hide();
										
									//}
								},
								failure : function(response, option) {
									console.log(response);
									Ext.Msg.alert('Error', option.result.file);
								}
							});
						}
					}]
			}]
});
oms.task.showTaskInfoEdit=function(taskID)
{
	var tp=Ext.getCmp("taskitem_"+taskID);
	console.log(tp.task);
	Ext.getCmp('taskstatuscombo').getStore().setData(oms.task.statuslist);
	Ext.getCmp('edittaskinfopanel').getForm().loadRecord({getData:function(){return tp.task;}});
	oms.task.EditTaskInfoPanel.show();
};
oms.task.updateTaskData=function(fdata,task)
{
	// first update task information
	task.owner=fdata.owner;
	task.status=fdata.status;
	task.targetDate=fdata.targetDate;
	Ext.get("task_ownerlbl_"+task.id).dom.innerText=task.owner;
	Ext.get("task_statuslbl_"+task.id).dom.innerText=task.status;
	Ext.get("task_tgtdtlbl_"+task.id).dom.innerText=task.targetDate;
}
oms.task.EditTaskInfoPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Edit Task Info',
	bodyPadding: 10,
	width:360,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	items:[
		{
			xtype:'form',
			id: 'edittaskinfopanel',
			border:0,
			defaultType:'textfield',
			layout:'vbox', 
			bodypadding:'10 10 10 10',
			
		items:[
		{
			xtype:'textfield',
			name:'id', 
			fieldLabel:'Task ID:', 
			margin: '0 2 5 15',
			labelCls:'omslabelstyle',
			fieldCls:'omsfieldstyle'
		},
		{
			xtype:'combobox',
			name:'status', 
			id:'taskstatuscombo',
			valueField:'status',
			displayField:'status',
			queryMode: 'local',
            typeAhead: true,
    		fieldLabel:'Task Status:',
			margin: '0 2 5 15',
			store:Ext.create('Ext.data.ArrayStore', {fields: [{name: 'status' }]}),
			labelCls:'omslabelstyle',
			fieldCls:'omsfieldstyle'
		},
		{
			name:'owner', 
			xtype:'combobox',
			id:'taskownercombo',
			valueField:'userId',
			displayField:'username',
			queryMode: 'local',
            typeAhead: true,
			store:Ext.create('Ext.data.JsonStore', {fields: [{name: 'userId' },{name:'username'}]}),
			fieldLabel:'Task Owner:', 
			margin: '0 2 5 15',
			labelCls:'omslabelstyle',
			fieldCls:'omsfieldstyle'
		},
		{
			name:'targetDate',
			fieldLabel:'Target Date', 
			margin: '0 2 5 15',
			xtype:'datefield',
			labelCls:'omslabelstyle',
			allowBlank:false,
			fieldCls:'omsfieldstyle'
			},
	],
	buttons:[
		{
			text:"Update Task",
			handler: function(){		
				var formdata = Ext.getCmp("edittaskinfopanel").getForm().getValues();
				Ext.Ajax.request({
					url : "api/project/task",
					method : 'PUT',
					jsonData : JSON.stringify(formdata),
					success : function(response, option) {
						console.log(response);
						var respObj = Ext.decode(response.responseText);
						Ext.Msg.alert(respObj.status, respObj.message);
						if (respObj.status === 'success') {
							oms.task.EditTaskInfoPanel.hide();
							var taskID=formdata.id;
							var tp=Ext.getCmp("taskitem_"+taskID);
							var task=tp.task;
							oms.task.updateTaskData(formdata,task);
						}
					},
					failure : function(response, option) {
						console.log(response);
						Ext.Msg.alert('Error', response.responseText);
					}
				});	
			}},
			{
				text:'Delete Task',
				handler:function(){
					alert("All document/notes and associated information will be removed.");
				}
			}
		]}]
});
oms.task.createTaskMainPanel=function(task)  // proj is the json data for the project
{
	var infop=oms.task.createTaskInfoPanel(task.taskinfo) ;
	var clgrid=oms.task.createTaskCommentPanel(task.commentlist,1);
	var dlgrid=oms.task.createTaskDocumentPanel(task.doclist,1);
	//var ulgrid=oms.project.createUserPanel(proj.userlist);
	//console.log(infop);
	var mainpanel=Ext.create('Ext.panel.Panel',{
		id:"taskPanel"+task.taskID,
		layout:'vbox',
		title:"TASK : --  ["+task.taskname+"]",
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

oms.task.statuslist=[['Not Started'],['In Progress'],['Completed'],['Withdraw'],['Pending'],['Review']];
oms.task.commentlist=
	[
	 [1,"Seng Tang","03/20/2016","comment title 1","comment text 1, blar blar blar"],
	 [2,"Wu Kong","02/12/2016","comment title 2","comment text 2, blar blar blar"],
	 [3,"Wu Kong","02/11/2016","comment title 3","comment text 3, blar blar blar"],
	 [4,"Ba Jie","02/02/2016","comment title 4","comment text 4, blar blar blar"],
	 [5,"Seng Tang","01/02/2016","comment title 5","comment text 5, blar blar blar"],
	 [6,"Seng Tang","01/01/2016","comment title 6","comment text 6, blar blar blar"]
	];