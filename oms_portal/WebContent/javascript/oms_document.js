/*
* {
xtype: 'component',
html: '<iframe src="data/jpmcHelp.pdf" width="100%" height="100%"></iframe>',
}
*/

oms.doc={}; // project builder
// this will return a fieldSets
oms.doc.convertDocInfo=function(dinfo){
	data={};
	data.docname=dinfo.name;
	data.docID=dinfo.documentId;
	proj=dinfo.project[0];
	data.pname=proj.projectInfo.projName;
	data.pID=proj.projectInfo.projId;
	// find task and doc inside task
	tasks=proj.tasks;
	var found=false;
	for(var t of tasks)
		{
			if(t.docs)
			{
				for(var d of t.docs)
				{
					if(d.documentId==data.docID)
					{
						data.task=t.description;
						data.doctype=d.doctype;
						found=true;
						break;
					}
				}
				if(found) break;
			}
		}
	return data;
};
oms.doc.createDocInfoPanel=function(dinfo) // json object of the project info
{
	console.log("doc info");
	console.log(dinfo);
	// now build form data
	data=oms.doc.convertDocInfo(dinfo);
	var panel=Ext.create('Ext.form.Panel', {
		// title:"Project Information:",
		margin:'2 2 2 2', 
		width:'100%',
		layout:'vbox',
		buttons:[
			{text:"View Project",
				handler: function()
				{
					oms.project.openProject(data.pID);
				}
			}
		],
		items:[
			{
				xtype:'fieldset',
				title:'Basic Info',
				width:'100%',
				margin:'2 2 2 2',
				layout:'vbox', 
				items:[
			{
				xtype:'displayfield',
				name:'docname',
				fieldLabel:'ID:',
				labelWidth:80,
				margin:'0 5 0 5',
				value: data.docID 
				},		
			{
				xtype:'displayfield',
				name:'docname',
				fieldLabel:'Document Name:',
				labelWidth:80,
				margin:'0 5 0 5',
				value: data.docname 
				},
			{
				xtype:'displayfield',
				name:'pname',
				fieldLabel:'Project',
				labelWidth:80,
				margin:'0 5 0 5',
				value: '<font color=#336699>'+data.pname+'</font>' 
				},

			{ 
			xtype:'displayfield',
			name:'taskname',
			labelWidth:80,
			fieldLabel:'Task',
			margin:'0 5 0 5',
			value:'<font color=#336699>'+data.task+'</font>'
			},
			{
			xtype:'displayfield',
			name:'doctype',
			fieldLabel:'Type',
			labelWidth:80,
			margin:'0 5 0 5',
			value: '<font color=green>'+data.doctype+'</font>' 
			}
			] 
			}
		]
		});
		return panel;
};


oms.doc.isImage=function(fn)
{
	var imagetag='jpgjpeggifpngbmp';
	fn=fn.toLowerCase();
	fn_ary=fn.split('.');
	if(imagetag.indexOf(fn_ary[1])>-1)
		{
			return true;
		}
	else
		{
			return false;
		}
};
oms.doc.createSearchDocMainPanel=function(doc,start,end,query)
{
	var infop=oms.doc.createDocInfoPanel(doc) ;
	if(doc ==null)
	{
		alert("no document referrence found!!")
		return;
	}
	console.log(doc);
	var filename=doc.name.toLowerCase();
	var mimetype="pdf";
	if(oms.doc.isImage(filename))
	{
		mimetype='image';
	}
	
	var htmlstr="";
	if(mimetype=='image')
		{
			htmlstr='<img src="'+doc.url+'"/>';
		}
	else
		{
		    url=doc.url;
		    if(start&&end)
		    {
		    	if(start==end)
		    	{
		    		url="api/document/download/"+doc.documentId+"/"+start+"/"+query;
		    	}
		    	else{
		    		url="api/document/download/"+doc.documentId+"/"+start+"-"+end+"/"+query;
		    	}
		    
		    }
			htmlstr='<iframe src="'+url+'" width="100%" height="100%"></iframe>'
		}
	// now build html
	var mainpanel=Ext.create('Ext.tab.Panel',{
		//layout:'hbox',
		title:"DOC: -- ["+doc.name+"]--["+query+"]",
		border:true,
		items:[ 
			{
			xtype:'component',
		//	id:'doccontent_'+doc.documentId,
			title:'Content',
			width:'72%',
			height:'100%',
			scrollable:true,
			margin:'2 2 2 2', 
			defaults:{
			bodypadding:10,
			scrollable:true,
			border:true
			},
			html: htmlstr
			},
			{
			xtype:'panel',
			width:'28%', 
			title:'Metadata',
			layout:'vbox',
			items:[infop]
			}
			
		]

		}); 

	return mainpanel;
};
oms.doc.createDocMainPanel=function(doc) 										// project
{
	var infop=oms.doc.createDocInfoPanel(doc) ;

	// var ulgrid=oms.project.createUserPanel(proj.userlist);
	// console.log(infop);
	if(doc==null)
	{
		alert("document not avaliable!!");
		return;
	}
	var filename=doc.name.toLowerCase();
	var mimetype="pdf";
	if(oms.doc.isImage(filename))
	{
		mimetype='image';
	}
	
	var htmlstr="";
	if(mimetype=='image')
		{
			htmlstr='<img src="'+doc.url+'"/>';
		}
	else
		{
			htmlstr='<iframe src="'+doc.url+'" width="100%" height="100%"></iframe>'
		}
	// now build html
	var mainpanel=Ext.create('Ext.tab.Panel',{
		id:"docPanel"+doc.documentId,
		title:"DOC: -- ["+doc.name+"]",
		border:true,
		items:[ 
			{
			xtype:'component',
			id:'doccontent_'+doc.documentId,
			width:'72%',
			title:'Content',
			height:'100%',
			scrollable:true,
			margin:'2 2 2 2', 
			defaults:{
			bodypadding:10,
			scrollable:true,
			border:true
			},
			html: htmlstr
			},
			{
			xtype:'panel',
			width:'28%', 
			title:'Metadata',
			layout:'vbox',
			items:[infop]
			}
		]
		}); 

	return mainpanel;
};
oms.doc.openSearchDoc=function(docID,spage,epage,query){
		// now need to ajax calls
	    	 var myMask = Ext.MessageBox.wait("Loading Document...."+docID);
		Ext.Ajax.request({
			url:'api/document/'+docID,
			success:function(response)
			{
				var obj=Ext.JSON.decode(response.responseText);
				//console.log(obj);
				var doc=obj.result;
				var dpanel=oms.doc.createSearchDocMainPanel(doc,spage,epage,query);
				Ext.getCmp('centerViewPort').add(dpanel);
				Ext.getCmp('centerViewPort').setActiveTab(dpanel);
				setTimeout(oms.loadUserDoc,2000);
				myMask.close();
			},
			failure: function(response) 
			        { 
			            console.log(response.responseText); 
			        } 
		});
};
oms.doc.openDoc=function(docID){
    if(Ext.getCmp('docPanel'+docID)!=null)
	{
		Ext.getCmp('centerViewPort').setActiveTab(Ext.getCmp('docPanel'+docID));
	}
    else{
	// now need to ajax calls
    	 var myMask = Ext.MessageBox.wait("Loading Document...."+docID);
	Ext.Ajax.request({
		url:'api/document/'+docID,
		success:function(response)
		{
			var obj=Ext.JSON.decode(response.responseText);
			console.log("fetching document");
			console.log(obj);
			var doc=obj.result;
			var dpanel=oms.doc.createDocMainPanel(doc);
			Ext.getCmp('centerViewPort').add(dpanel);
			Ext.getCmp('centerViewPort').setActiveTab(dpanel);
			setTimeout(oms.loadUserDoc,2000);
			
			myMask.close();
		},
		failure: function(response) 
		        { 
		            console.log(response.responseText); 
		        } 

	});

}
};
oms.doc.buildSearchDocs=function(docs){
    var res="<ul>";
	for(var i=0;i<docs.length;i++)
	{
	    var doc=docs[i];
		if(docs[i].name!=null)
			{
				res=res+"<li>"
				res=res+"<font color=green>"+doc.name+"</font> -- by "+doc.author;
				res=res+"</font><p>......<a href='#' onclick='oms.doc.openDoc("+doc.id+");return false;'>Open Document</a>";
				res=res+"</li>";
			}
	}
	res=res+"</ul>"
	
	return res;
};

oms.doc.preDocSearch=function(key){
	 Ext.getCmp('searchPanel').expand();
	Ext.getCmp('docsearchkey').setValue(key);
//	Ext.getCmp('searchedDocuments').update("loading....");
};
oms.doc.buildTreeStore=function(searchkey,nodes){
	var stree=Ext.create('Ext.data.TreeStore',{
		root:{
			text:'Search Result for:['+searchkey+']',
			expanded:true,
			children:nodes
	//		loaded:true
		}
	});
	return stree;
};
oms.doc.openDocumentPanel=Ext.create('Ext.window.Window',{
	frame: true,
	closable:true, 
	title: 'Searching Document',
	bodyPadding: 10,
	width:600,
	scrollable:true,
	closeAction: 'hide',
	layout:'vbox',
	height:540,
	items:[{
			xtype:'displayfield',			
			margin: '0 2 2 15',
			value:'Pease enter the searching key words:'
		},
		{
			xtype:'textfield',
			width:'92%',
			margin: '0 2 2 15',
			id:'docsearchkey1'
		},
		{
			 xtype:'tabpanel',				   
			   id:'searchtabpanel1',	
			   width:'92%',
			   height:360,
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
				var urlstr="api/document/search?query="+Ext.encode(key);
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
							var store=oms.doc.buildTreeStore(key,respObj.result);
							var colors=["#ffcccc","#ccffcc","orange"];
							var index=Ext.getCmp('searchtabpanel').items.length;
							var c=colors[index%3];
							var panel=Ext.create('Ext.tree.Panel', {
								style: {borderColor:c, borderStyle:'double', borderWidth:'3px'},
								title: '['+key+':]', 
								closable: true,
							    autoScroll:true,
							    scroll:'vertical',
							    store:store,
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
					          /*  	if(r.data&&r.data.startPage)
					            	{
					            		ccg.ui.updateSelectedContent(r.data,searchkey);
					            		if(r.data.text.indexOf("Article")>-1)
					            		{
					            			if(r.data.expanded!=true)
					            			{
					            				this.expandNode(r,true);
					            			}
					            		}
					            	}	
					            	// now check if the tree is expanded
					            	*/
					            }
							    }
							});
							Ext.getCmp('searchtabpanel').add(panel).show();
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
		]
});　
oms.doc.sample1={
		projectID:1,
		taskID:15,
		docID:10,
		projectname:'SVT Tracking System',
		taskname:'Review Proposal Draft',
		docname:'Proposal Draft', 
		docinfo:{}
};