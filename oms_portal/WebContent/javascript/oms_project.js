
oms.project={};   // project builder
// this will return a fieldSets
oms.project.createProjInfoPanel=function(pinfo) // json object of the project info
{
	var panel=Ext.create('Ext.form.Panel',{
		title:"Info",
		width:'99%',
		margin:'2 2 2 2',
		buttons:[
		         {text:"Save"},
		         {text:"Update Project Status"},
		         {text:'Send Notification'}
		        ],
		//buttonAlign:'left',
		items:[
		       { 
		    	  xtype:'fieldset',
		    	  title:'Customer Info',
		    	  margin:'2 2 2 2',
		    	  defaultType:'textfield',
		    	  layout:'vbox',		    	   	  		    	
		    	  items:[
		    	     {
		    	    	xtype: 'container',
		    	    	layout: 'hbox',
		    	    	margin: '0 0 5 5',		    	    	 
		    	    	defaultType:'textfield',
		    	        items:[
		    	    	 {
		    	    		 name:'projAgency',
		    	    		 labelWidth:110,
		    	    		 flex:1,		    	    		 		    	    	
		    	    		 fieldLabel:'Project Agency:'
		    	    		 	 
		    	    	 },
		    	    	 {
		    	    		 name:'projOrg',
		    	    		 labelWidth:110,
		    	    		 flex:2,		  
		    	    		 margin:'0 0 0 5',
		    	    		 fieldLabel:"Organization:"
		    	    	 },
		    	    	 {
		    	    		 name:'projSubOrg',
		    	    		 labelWidth:110,
		    	    		 flex:3,
		    	    		 xtype:'textfield',		 
		    	    		 margin:'0 0 0 5',
		    	    		 fieldLabel:'Sub Organization:'
		    	    	 }
		    	    	]
		    	     },		    
		    	     {
		    	    	xtype:'container',
		    	    	layout:'hbox',
		    	    	margin: '0 0 5 0',		    	    	 
		    	    	defaultType:'textfield',
		    	    	items:[
		    	     	{		    	    	
		    	    	    	   name:'projloc',
		    	    	    	   fieldLabel:'Project Location',
		    	    	    	   labelWidth:110,		    	    	    	   
		    	    	    	   margin:'0 0 0 5'
		    	    	    		   
		    	     	},
		    	     	{
		    	    	 		   name:'contactoffice',
		    	    	 		  labelWidth:110,
		    	    	 		   fieldLabel:'Contact Office',
		    	    	 		   margin:'0 5 0 5'
		    	    	 		   
		    	     	},
		    	     	{
		    	     				name:'projcategory',
		    	     				fieldLabel:'Category',
		    	     				labelWidth:110,
		    	     				margin:' 0 0 0 5'
		    	     	}
		    	     	]    	     
		    	     }
		    	  ]
		      },
		      {
		    	 xtype:'fieldset',
		    	 title:'Prime Info',
		    	 layout:'hbox',
		    	 margin:'2 2 5 5',
		    	 defaultType:'textfield',
		    	 items:[
		    	       {
		    	    	   xtype:'checkbox',
		    	    	   name:'isPrimeProject',
		    	    	   labelWidth:110,
    	    	    	   margin:'0 35 0 5',
		    	    	   boxLabel:'is Prime?'
		    	       },
		    	       {
		    	    	   name:'primeName',
		    	    	   fieldLabel:'Prime Name:',
		    	    	   labelWidth:110,
    	    	    	   margin:'0 0 5 5'
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
		    	    	 fieldLabel:'Target Date',		    	    	
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
		      }
		       
		],
	});
	return panel;
};

oms.project.createTaskItemPanel=function(taskdata)
{
	// build header
	var h_html='<table width=100% style="background-color:#eeeeff;font-size:10pt"><tr>';
	h_html=h_html+"<td>#"+taskdata[1]+"</td>";
	h_html=h_html+"<td width=40%><b>Task:</b><font color=green>"+taskdata[2]+"</font></td>";
	h_html=h_html+"<td width=15%><b>Owner:</b><font color=#336699>"+taskdata[3]+"</font></td>";			
	h_html=h_html+"<td width=15%><b>Status:</b><font color=green>"+taskdata[4]+"</font></td>";
	h_html=h_html+"<td width=15%><b>Target Date:</b><font color=green>"+taskdata[5]+"</font></td>";
	h_html=h_html+'<td width=10%><img src="css/images/shared/icons/fam/add.png"><img src="css/images/shared/icons/fam/delete.gif"><img src="css/images/shared/icons/fam/information.png"></td>';
	h_html=h_html+"</tr></table>";
	var taskid="task"+taskdata[0];
	// now build commend and document panel
	var clgrid=oms.task.createTaskCommentPanel(taskdata,taskid);
	var dlgrid=oms.task.createTaskDocumentPanel(taskdata,taskid);	
	console.log(clgrid);
	var taskpanel=Ext.create('Ext.panel.Panel',{
		id:taskid,
			scrollable:true,
	     	header:{items:[{xtype:'label',width:'98%',html:h_html}]},
		  //  title:'task 1',
		    collapsed: true,
	        collapsible: true,	
	        titleCollapse: true,
	        width:'99%',
	        layout:'vbox',	        
	      	
	        items:[
	               	{
	               		xtype:'tabpanel',
	               		id:'taskDetails'+taskid,
	               		width:'99%',
	            //   	   tabPosition: 'left',
	               		margin:'2 2 2 12',
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
	              {text: "#",  dataIndex: 'seq'},
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
	              {text: "Owner",  dataIndex: 'owner',width:160},
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
	  //  title:'task 1',
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
		items:[	grid	    
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
	              {text: "type",  dataIndex: 'doctype',width:180},
	              {text: "Name", flex:2,dataIndex: 'docname'},	             
	              {text: "Task", flex:1,dataIndex: 'task'},
	              {text: "Upload Date", dataIndex: 'uploaddate',formatter: 'date("m/d/Y")'},
	              {text: "User",  dataIndex: 'user',width:160},	
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
	               {  text:'Add User' }	            
	           ]
	});
	return grid;
};
oms.project.createProjectPanel=function(proj)  // proj is the json data for the project
{
	var infop=oms.project.createProjInfoPanel(proj.info) ;
	var tlgrid=oms.project.createTaskListPanel(proj.tasklist,1);
	var dlgrid=oms.project.createDocumentPanel(proj.doclist);
	var ulgrid=oms.project.createUserPanel(proj.userlist);
	//console.log(infop);
	var mainpanel=Ext.create('Ext.panel.Panel',{
		id:"projectPanel"+proj.projectID,
		layout:'vbox',
		title:"PROJECT : --  ["+proj.name+"]",
		border:true,
		items:[
		       {
		    	   xtype:'fieldset',
		    	   title:'Status and Manager',
		    	   margin:'2 2 2 2',
		    	   layout:'hbox',		   
		    	   items:[
		    	          {
		    	        	  xtype:'displayfield',
		    	        	  name:'projstatus',
		    	        	  fieldLabel:'Project Status',
		    	        	  labelWidth:110,
		    	        	  margin:'0 60 0 5',
		    	        	  value:'<span style="color:green; ">In Progress</span>'		    	        	  
		    	          },
		    	          {
		    	        	  xtype:'textfield',
		    	        	  name:'projmanager',
		    	        	  labelWidth:110,
		    	        	  fieldLabel:'Project Manager',
		    	        	  value:'Alpha Delta'
		    	          },
		    	          {
			    	        	 xtype:'button',
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
		    	 tabPosition: 'right',			    	
		    	 items:[
		    	   tlgrid,
		    	   infop,		    	   
		    	   dlgrid,
		    	   ulgrid
		    	 ]
		     }
		]
		
	});
	
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