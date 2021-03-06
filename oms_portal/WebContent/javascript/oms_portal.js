oms={};
oms.admin={};
oms.projmgr={};
oms.userpref={};

function calnextyear(start,rate,addon,addonrate)
{
	var res=start*rate+addon*addonrate;
	return res;
}

function displayRate()
{
	var start=50000;
	var rate=1.36;
	var addon=20000;
	var addonrate=1.18;
	var res=start;
	for(var year=2017;year<=2027;year++)
	{
		res=calnextyear(res,rate,addon,addonrate);
		console.log("year of "+year+" .... "+res);
	}
	}
	Ext.define('com.ccg.oms.portalHeader', {
		extend: 'Ext.Container',
		xtype: 'oms-portal-header',
		title: document.title,
		cls: 'app-header',
		height: 40,
		layout: {
			type: 'hbox',
			align: 'middle'
		},
		items: [{
			xtype: 'component',
			cls: 'oms-app-logo'
		}, 
		{
			xtype: 'component',
			cls: 'app-header-title',
			html: document.title,
			flex: 73 / 100
		}, 
		{
			xtype: 'label',
			id: 'userProfile',
			style:{
			         'font-size':'12px'
			        },
			flex: 12 / 100
		},
		{
			  xtype: 'button',
			  text:'Search',
			  handler: function() {
				  Ext.getCmp('searchPanel').expand();
			    },
			  flex: 5 / 100
		},
		{
			xtype:'label',
			  flex: 1 / 100
		},
		{
			 xtype: 'button',
			  text:'Logout',
			  flex: 5 / 100,
			  handler: function(){
				  Ext.Ajax.request({
						url : "api/user/logoff",
						method : 'GET',
						success : function(response, option) {
							console.log(response);
							window.location.reload();
						},
						failure : function(response, option) {
							console.log(response);
							window.location.reload();
						}
					});	
			  }
		},
		{
			xtype:'label',
			  flex: 1 / 100
		},]
	});

oms.loadUserDoc=function(mask){
	Ext.Ajax.request({
		url : "api/user/viewedDocuments",
		method : 'GET',
		timeout:120000,
		success : function(response, option) {
			//console.log(response);
			var respObj = Ext.decode(response.responseText);
		//	Ext.Msg.alert(respObj.status, respObj.message);
		    
			if (respObj.status === 'success') {
				// now we need to render the documents
				var l_doc=respObj.result;
			//	console.log(l_proj);
		    //  now load search keyworkds
				//console.log("call key words");
				Ext.Ajax.request({
					url : "api/user/searchedKeyWords",
					method : 'GET',
					success : function(response, option) {
						
						var keys= Ext.decode(response.responseText).result;
						var htmlstr=oms.ui.getUserDocumentHtml(l_doc,keys);
						Ext.getCmp('mydocpanel').update(htmlstr);
						if(mask)
							{
							  mask.close();
							}
					},
					failure : function(response, option) {
					console.log(response);
					}
				});
			}
		},
		failure : function(response, option) {
			console.log(response);
			Ext.Msg.alert('Error', response.responseText);
		}
	});
}
oms.resetProjectSearch=function()
{
	var cc=Ext.getCmp("projsearchkey");
	var data= cc.getStore().getData();
	cc.clearValue();
	if(cc.lastQuery!=null)
		{
			cc.lastQuery=null;
			cc.getStore().reload();
		}
	cc.clearInvalid();
};
oms.loadUserProject=function()
{
	Ext.Ajax.request({
		url : "api/user/viewedProjects",
		method : 'GET',
		success : function(response, option) {
			//console.log(response);
			var respObj = Ext.decode(response.responseText);
		//	Ext.Msg.alert(respObj.status, respObj.message);
		    
			if (respObj.status === 'success') {
				// now we need to render the documents
				var l_proj=respObj.result;
			//	console.log(l_proj);
				var htmlstr=oms.ui.getUserProjectHtml(l_proj);
				Ext.getCmp('myprojpanel').update(htmlstr);
			}
		},
		failure : function(response, option) {
			console.log(response);
			Ext.Msg.alert('Error', response.responseText);
		}
	});
};　
Ext.onReady(function(){
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		renderTo : Ext.getBody(),
		id: 'mainViewPort',
		monitorResize: true,
		items: [{
			region: 'north',
			xtype: 'oms-portal-header',
			id: 'oms-app-header'
		},
		{
			region: 'west',
			collapsible: true,
			title: 'OMS Main Menu',
			id: 'oms-nav-menu',
			width: 240,
			split: true,
			layout: {
				type: 'accordion',
				pack: 'start',
				align: 'stretch'
			},
			bodyPadding: 2,
			defaults: {
						bodyPadding: 2
					},
			items: [
					{title:'My Projects',
						id:'myprojpanel',
						html:""
					}, 
					{title:'My Documents',
						id:'mydocpanel',
						html:''
					},
					{
						title:'Reports',
						html:oms.ui.getReportHtml()
					},
					{title:'Admin',
						html:oms.ui.getAdminHtml()
					}
					],
					listeners:{
						collapse:function(){
							Ext.getCmp('centerViewPort').doLayout();
						},
						expand:function(){
							Ext.getCmp('centerViewPort').doLayout();
						}
					}
			},
			{
				region:'east',
				frame: true,
				collapsible: true,
				id:'searchPanel',
				title: 'Searching Document',
				bodyPadding: 5,
				scrollable:true,
				layout:'vbox',
				collapsed:true,
				width:480,
				items:[{
						xtype:'displayfield',			
						margin: '0 2 2 15',
						value:'Pease enter the searching key words:'
					},
					{
						xtype:'textfield',
						width:'92%',
						margin: '0 2 2 15',
						id:'docsearchkey'
					},
					{
						 xtype:'tabpanel',				   
						   id:'searchtabpanel',	
						   width:'92%',
						   minHeight:480,
						   flex:1,
						   margin: '5 5 5 15',
						    activeTab:0,
						items:[
						   
						]

					}
				],
				buttons:[
					{
						text:"Search",
						handler: function()
						{
							var key=Ext.getCmp('docsearchkey').getValue();
							var urlstr="api/document/search2?query="+Ext.encode(key);
							var myMask = Ext.MessageBox.wait("Processing....","Searching Article...");
						
							Ext.Ajax.request({
								url : urlstr,
								method : 'GET',
								timeout:120000,
								success : function(response, option) {
									//console.log(response);
									console.log(response);
									var respObj = Ext.decode(response.responseText);
									
								//	Ext.Msg.alert(respObj.status, respObj.message);
					//				console.log(respObj);
									if (respObj.status === 'success') {
										// now we need to render the documents
									//	var htmlstr=oms.doc.buildSearchDocs(respObj.result.response.docs);
										console.log(respObj);
										var store=oms.doc.buildTreeStore(key,respObj.result.docs);
										var colors=["#ffcccc","#ccffcc","orange"];
										var index=Ext.getCmp('searchtabpanel').items.length;
										var c=colors[index%3];
									
										var treepanel=Ext.create('Ext.tree.Panel', {
											style: {borderColor:c, borderStyle:'double', borderWidth:'3px'},
										    autoScroll:true,
										    scroll:'vertical',
										    flex:1,
										    store:store,
										    region:'center',
										    listeners:{
										    itemclick: function(s,r) {
										    	if(r.data.id=='root'){
										    			return;
										    	}
								            	var d=r.data;
								            	console.log(d);
								            	if(d.documentId&&d.startPage&&d.endPage){
								            		oms.doc.openSearchDoc(d.documentId,d.startPage,d.endPage,key);
								            	}
								            	else if(d.documentId)
								            	{
								            		oms.doc.openDoc(d.documentId);
								            	}
								            }
										    }
										});
										var filterpanel=oms.doc.makeFilterPanel(respObj.result.docs,key,treepanel,respObj.result.docType);
										var cpanel=Ext.create('Ext.form.Panel', {
											// title:"Project Information:",
											title:'['+key+']',
											closable:true,
											margin:'2 2 2 2', 
											autoScroll:true,
											layout:'border',
											scroll:'vertical',
											height:'98%',
											items:[filterpanel,treepanel]
										});
										Ext.getCmp('searchtabpanel').add(cpanel).show();
										setTimeout(oms.loadUserDoc,2000);
										myMask.close();
									}
									else{
										console.log(response);
										alert('Search Error or No Data!');
										myMask.close();
									}
								},
								failure : function(response, option) {
									console.log(response);
									Ext.Msg.alert('Error', response.responseText);
									myMask.close();
								}
							});
							
						}

					}
					]},
					{
								
						region:'center',
						id:'centerViewPort',
						xtype:'tabpanel',
						border:true,
						frame:true,
						defaults: {
						bodyPadding: 10,
						height:500,
						scrollable: true,
						closable: true
						},
						items:[]
					}

			]
	});
// load sample project view

	//var ppanel=oms.project.createProjectPanel(oms.project.sample1);
	//var tpanel=oms.task.createTaskMainPanel(oms.task.sample1);
	//var dpanel=oms.doc.createDocMainPanel(oms.doc.sample1);
	oms.adminpanel=oms.admin.createAdminPanel();
	var viewport=Ext.getCmp('centerViewPort');
	//viewport.add(ppanel);
	//viewport.add(tpanel);
//	viewport.add(dpanel);
//	viewport.add(adminpanel);
	viewport.doLayout();
//	viewport.setActiveTab(2);
	//viewport.add(oms.project.createNewProjPanel);
	var myMask = Ext.MessageBox.wait("Initialized Data Please Wait...");
	oms.admin.refreshData();
	oms.loadUserProject();
	oms.loadUserDoc(myMask);
});