/*
* {
xtype: 'component',
html: '<iframe src="data/jpmcHelp.pdf" width="100%" height="100%"></iframe>',
}
*/

oms.doc={}; // project builder
// this will return a fieldSets
oms.doc.createDocInfoPanel=function(dinfo) // json object of the project info
{
	var panel=Ext.create('Ext.form.Panel', {
		// title:"Project Information:",
		margin:'2 2 2 2', 
		width:'100%',
		layout:'vbox',
		buttons:[
			{text:"View Project"}
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
			name:'taskname',
			labelWidth:80,
			fieldLabel:'Task',
			margin:'0 5 0 5',
			value:'<font color=#336699>Review Proposal Draft</font>'
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
			xtype:'displayfield',
			name:'docuser',
			fieldLabel:'User',
			labelWidth:80,
			margin:'0 5 0 5',
			value: '<font color=green>Will Smith</font>' 
			
			},
			{
			xtype:'displayfield',
			name:'doctype',
			fieldLabel:'Type',
			labelWidth:80,
			margin:'0 5 0 5',
			value: '<font color=green>Proposal Draft</font>' 
			
			},
			{
			xtype:'displayfield',
			name:'docrestriced',
			fieldLabel:'Restricted',
			labelWidth:80,
			margin:'0 5 0 5',
			value: '<font color=green>N</font>' 
			
			},
			{
			xtype:'displayfield',
			name:'docrequired',
			fieldLabel:'Required',
			labelWidth:80,
			margin:'0 5 0 5',
			value: '<font color=green>Y</font>' 
			}
			] 
			}
			,
			{
				xtype:'fieldset',
				title:'Content and Topics',
				margin:'2 2 12 2',
				width:'100%',
				layout:'vbox',
				items:[
					{xtype:'displayfield',
						value:'Office Automation'},
					{
						xtype:'displayfield',
						value:'System upgrade'
					},
					{
						xtype:'displayfield',
						value:'RFP'
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
oms.doc.createDocMainPanel=function(doc) // proj is the json data for the											// project
{
	var infop=oms.doc.createDocInfoPanel(doc) ;

	// var ulgrid=oms.project.createUserPanel(proj.userlist);
	// console.log(infop);
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
	var mainpanel=Ext.create('Ext.panel.Panel',{
		id:"docPanel"+doc.documentId,
		layout:'hbox',
		title:"DOC: -- ["+doc.name+"]",
		border:true,
		items:[ 
			{
			xtype:'component',
			id:'doccontent_'+doc.documentId,
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
			
			layout:'vbox',
			items:[infop]
			}
			
		]

		}); 
	return mainpanel;
};

　
oms.doc.sample1={
projectID:1,
taskID:15,
docID:10,
projectname:'SVT Tracking System',
taskname:'Review Proposal Draft',
docname:'Proposal Draft', 
docinfo:{}
};