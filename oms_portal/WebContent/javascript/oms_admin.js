// this is the admin js
// Doctype Editing / Search type Editing / User Role editing /
// Task Template Editing
oms.admin.createUserAdminPanel=function()
{
	var ustore=Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId: 'ulistStore',
		fields: [
			{name: 'username' }, 
			{name: 'name'},
			{name: 'email'},
			{name: 'role'},
			{name: 'fullaccess',type:'boolean'},
			{name: "createdTS",type:'date'}
			]
	});
	var grid=Ext.create('Ext.grid.Panel',{
		id:'userlistadmingrid',
		store:ustore, 
		scrollable:true,
		tbar:[
			{
				text:"Add New User" ,
				listeners:{
					click:
					{
						element:'el',
						fn:function(){
							Ext.getCmp('btusersave').setVisible(true);
							Ext.getCmp('btuserdelete').setVisible(false);
							Ext.getCmp('btuserupdate').setVisible(false);
							Ext.getCmp("userEditform").getForm().reset();
							Ext.getCmp('userpassword').setVisible(true);
							Ext.getCmp('userverify').setVisible(true);
							Ext.getCmp('username').setReadOnly(false);
							oms.admin.userEditPanel.setTitle('Add New User'),
							oms.admin.userEditPanel.show();
						}
					}
				}
			}
			],

			columns: [
				{text:"Restricted Access",dataIndex:'fullaccess', width:160,
					renderer:function(val)
					{
						if(val==true)
						{
							return "<img src='css/images/sec.jpg' width=20 />"; 
						}
						return "";
					} 
				},
				{text: "Username", flex:2,dataIndex: 'username',
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
				{text: "Name", dataIndex: 'name',width:180}, 
				{text: "Created", dataIndex: 'createdTS',formatter: 'date("m/d/Y")'},
				{text: "Email", dataIndex: 'email',width:160}, 
				{text: "Role.",dataIndex:"role"	},
				{
		            xtype:'actioncolumn',
		            width:100,
		            items: [{
		                icon: 'css/images/shared/icons/fam/user_edit.png',  // Use a URL in the icon config
		                tooltip: 'Edit',
		                handler: function(grid, rowIndex, colIndex) {
		                    var rec = grid.getStore().getAt(rowIndex);
		                    console.log(rec);
		                    Ext.getCmp("userEditform").getForm().loadRecord(rec);
		                    console.log(rec.data);
		                    Ext.getCmp('btusersave').setVisible(false);
							Ext.getCmp('btuserdelete').setVisible(false);
							Ext.getCmp('btuserupdate').setVisible(true);
							Ext.getCmp('userpassword').setVisible(false);
							Ext.getCmp('userverify').setVisible(false);
							Ext.getCmp('username').setReadOnly(true);
							oms.admin.userEditPanel.setTitle('Update User'),
							oms.admin.userEditPanel.show();
		                }
		            },{
		                icon: 'css/images/shared/icons/fam/user_delete.png',
		                tooltip: 'Delete',
		                handler: function(grid, rowIndex, colIndex) {
		                    var rec = grid.getStore().getAt(rowIndex);
		                    console.log(rec);
		                    Ext.getCmp("userEditform").getForm().loadRecord(rec);
		                    Ext.getCmp('btusersave').setVisible(false);
		                    Ext.getCmp('btuserupdate').setVisible(false);
							Ext.getCmp('btuserdelete').setVisible(true);
							Ext.getCmp('userpassword').setVisible(false);
							Ext.getCmp('userverify').setVisible(false);
							oms.admin.userEditPanel.setTitle('Delete User');
							oms.admin.userEditPanel.show();
		                }
		            }]
		        }
				
				],
				columnLines: true, 
				title:'Users' 
	});
	return grid;
};

oms.admin.createAdminPanel=function()
{
	var ugrid=oms.admin.createUserAdminPanel();
	var dtgrid=oms.admin.createDoctypeAdminPanel();
	//var stgrid=oms.admin.createSearchTypeAdminPanel();
	var panel=Ext.create('Ext.panel.Panel',{
		id:"adminMain",
		layout:'vbox',
		title:"OMS Admin",
		border:true,
		items:[
			{
				xtype:'tabpanel',
				id:'adminDetails',
				width:'99%',
				margin:'2 2 2 2',
				flex:1, 
				defaults:{
					bodypadding:10,
					scrollable:true,
					border:true
				},
				//tabPosition: 'right', 
				items:[
					dtgrid,// doc type
					oms.admin.createProjectRoleTypeAdminPanel(),
					ugrid,// user
					oms.admin.createTLPanel(), // tasktemplate
					oms.admin.createPLPanel() // project template
					// oms.admin.userEditPanel
					]
			}
			]
	});
return panel;
};

oms.admin.userEditPanel=Ext.create('Ext.window.Window',{
	frame: true,
	id:'usereditpanel',
	name:'userform',
	float:true,
	closable:true, 
	title: 'Add New User',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 420,
	modal: false,
	items:[{xtype:'form',
		width:'98%',
		//id: 'userform',
		id:'userEditform',
		defaultType: 'textfield',
		fieldDefaults: {
			labelAlign: 'right',
			labelWidth: 150,
			msgTarget: 'side'
		},
		items:[
			{ 
				fieldLabel: 'User Name', 
				name: 'username', 
				emptyText: 'user id',
				id: 'username',
			},
			{ 
				fieldLabel: 'Password', 
				name: 'pass', 
				emptyText: 'password', 
				inputType: 'password',
				id: 'userpassword'
			},
			{ 
				fieldLabel: 'Verify', 
				name: 'pass', 
				emptyText: 'password', 
				inputType: 'password',
				id: 'userverify'
			},
			{
				fieldLabel: 'Name',
				emptyText: 'Name',
				name: 'name'
			}, 
			{
				fieldLabel: 'Company',
				name: 'company'
			}, 
			{
				fieldLabel: 'Email',
				name: 'email',
				vtype: 'email'
			},
			{ fieldLabel:'is Contractor',
				xtype:'checkbox',
				value:0,
				inputValue:true,
				uncheckValue:false,
				
				name:'iscontractor'
				},
				{
					fieldLabel:'Role',
					name:'role'
				},
				{
					fieldLabel:'Full Access',
					xtype:'checkbox',
					value:0,
					inputValue:true,
					uncheckValue:false,
					name:'fullaccess'
				}//,
				//{fieldLabel:'Created TS',xtype:'datefield', dataIndex: 'createdTS',formatter: 'date("m/d/Y")'},
			],
		}],
	
			buttons: [{
				text: 'Save',
				id:'btusersave',
				handler : function() {
					console.log("create new user");
					//var items = this.up('window').items.items;
					var formdata = this.up('window').down('form').getValues();;
					console.log(formdata);
					var jsonData2 = JSON.stringify(formdata);
					console.log(jsonData2);
					
					
					//console.log(formdata);
					// ajax call backend to create new user
					Ext.Ajax.request({
						url : "api/user",
						method : 'POST',
						jsonData : JSON.stringify(formdata),
						success : function(response, option) {
							console.log(response);
							var respObj = Ext.decode(response.responseText);
							Ext.Msg.alert(respObj.status, respObj.message);
							if (respObj.status === 'success') {
								oms.admin.userEditPanel.hide();
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
				text:'Delete',
				id:'btuserdelete',
				handler: function(){
					var formdata = Ext.getCmp("userEditform").getForm().getValues();
					var username = formdata['username'];
					console.log(username);
					Ext.Ajax.request({
						url: "api/user/" + username,
						method: 'DELETE',
						success: function(response, option){
							console.log(response);
							var respObj = Ext.decode(response.responseText);
							Ext.Msg.alert(respObj.status, respObj.message);
							if (respObj.status === 'success') {
								oms.admin.userEditPanel.hide();
							}
						},
						failure: function(response, option){
							console.log(response);
							Ext.Msg.alert('Error', response.responseText);
						}
					});
					
				}
			},
			{
				text: 'Update',
				id: 'btuserupdate',
				handler: function(){
					var formdata = Ext.getCmp("userEditform").getForm().getValues();
					console.log(formdata);
					Ext.Ajax.request({
						url : "api/user",
						method : 'PUT',
						jsonData : JSON.stringify(formdata),
						success : function(response, option) {
							console.log(response);
							var respObj = Ext.decode(response.responseText);
							Ext.Msg.alert(respObj.status, respObj.message);
							if (respObj.status === 'success') {
								oms.admin.userEditPanel.hide();
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



// doc type edit panel
oms.admin.DoctypeEditPanel=Ext.create('Ext.window.Window',{
	frame: true,
	float:true,
	closable:true, 
	title: 'Edit Doc Type',
	bodyPadding: 10,
	scrollable:true,
	closeAction: 'hide',
	width: 420,
	height:200,
	//modal: false,

	items:[{xtype:'form',	
		id:'doctypeEditForm',
		defaultType: 'textfield',
		fieldDefaults: {
			labelAlign: 'right',
			labelWidth: 150,
			bodyPadding: 10,
			},items:[
	{ fieldLabel: 'Doc Type', name: 'doctype', emptyText: 'New Doc Type' },
	{ fieldLabel: 'Description', name: 'description'},
	{ fieldLabel: 'Sample URL',name:'sampleURL'}]}
	],
	buttons: [{
			text: 'Save',
			id:'btdtsave',
			handler: function(){
				var formdata = Ext.getCmp("doctypeEditForm").getForm().getValues();
				console.log(formdata);
				Ext.Ajax.request({
					url : "api/projectadmin/docType",
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
			}
		},
		{
			text:'Delete',
			id:'btdtdelete',
			handler: function(){
				var formdata = Ext.getCmp("doctypeEditForm").getForm().getValues();
				console.log(formdata);
				Ext.Ajax.request({
					url : "api/projectadmin/docType/" + formdata['doctype'],
					method : 'DELETE',
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
			}
		}
		]
	});


oms.admin.createDoctypeAdminPanel=function(){
	var dtstore=Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId: 'dtStore',
		fields: [
			{name: 'doctype' }, 
			{name: 'description'},
			{name: 'sampleURL'},
			{name:'createTS'}
			] 
			});
	var grid=Ext.create('Ext.grid.Panel',{
			id:'dtadminngrid',
			store:dtstore, 
			scrollable:true,
	tbar:[
		{
			text:"Add New DocType" ,
			listeners:{
				click:
				{
					element:'el',
					fn:function(){
						Ext.getCmp('btdtsave').setVisible(true);
						Ext.getCmp('btdtdelete').setVisible(false);
						Ext.getCmp("doctypeEditForm").getForm().reset();
						oms.admin.DoctypeEditPanel.show();
					}
				}
			}
		}
		],

		columns: [
			{text: "Doc Type", dataIndex: 'doctype',width:180}, 
			{text: "Description", dataIndex: 'description',flex:1},
			{text: "Sample URL", dataIndex: 'sampleURL',flex:2},
			{
	            xtype:'actioncolumn',
	            width:100,
	            items: [{
	                icon: 'css/images/shared/icons/fam/user_edit.png',  // Use a URL in the icon config
	                tooltip: 'Edit',
	                handler: function(grid, rowIndex, colIndex) {
	                    var rec = grid.getStore().getAt(rowIndex);
	                    console.log(rec);
	                    Ext.getCmp("doctypeEditForm").getForm().loadRecord(rec);
	                    Ext.getCmp('btdtsave').setVisible(true);
						Ext.getCmp('btdtdelete').setVisible(false);
						oms.admin.DoctypeEditPanel.show();
	                }
	            },{
	                icon: 'css/images/shared/icons/fam/delete.gif',
	                tooltip: 'Delete',
	                handler: function(grid, rowIndex, colIndex) {
	                    var rec = grid.getStore().getAt(rowIndex);
	                    console.log(rec);
	                    Ext.getCmp("doctypeEditForm").getForm().loadRecord(rec);
	                    Ext.getCmp('btdtsave').setVisible(false);
						Ext.getCmp('btdtdelete').setVisible(true);
						oms.admin.DoctypeEditPanel.show();
	                }
	            }]
	        }
			],
			columnLines: true, 
			title:'Doctypes' 
	});
	return grid;
	};

oms.admin.ProjectRoleEditPanel=Ext.create('Ext.window.Window',{
		frame: true,
		float:true,
		closable:true, 
		title: 'Edit Project Role',
		bodyPadding: 10,
		scrollable:true,
		closeAction: 'hide',
		width: 480,
		//modal: false,

		items:[{xtype:'form',	
			id:'projroleEditForm',
			defaultType: 'textfield',
			fieldDefaults: {
				labelAlign: 'right',
				labelWidth: 150,
				bodyPadding: 10,
				},
				items:[
					{ fieldLabel: 'Role Name', name: 'roletype', emptyText: 'New Role Type' },
					{ fieldLabel: 'Description', name: 'description'}
					]
		}],
		buttons: [{
				text: 'Save',
				id:'btprsave',
				handler: function(){
					var formdata = Ext.getCmp("projroleEditForm").getForm().getValues();
					console.log(formdata);
					Ext.Ajax.request({
						url : "api/projectadmin/projectUserRoleType",
						method : 'POST',
						jsonData : JSON.stringify(formdata),
						success : function(response, option) {
							console.log(response);
							var respObj = Ext.decode(response.responseText);
							Ext.Msg.alert(respObj.status, respObj.message);
							if (respObj.status === 'success') {
								oms.admin.ProjectRoleEditPanel.hide();
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
				text:'Delete',
				id:'btprdelete',
				handler: function(){
					var formdata = Ext.getCmp("projroleEditForm").getForm().getValues();
					console.log(formdata);
					Ext.Ajax.request({
						url : "api/projectadmin/projectUserRoleType/" + formdata['roletype'],
						method : 'DELETE',
						success : function(response, option) {
							console.log(response);
							var respObj = Ext.decode(response.responseText);
							Ext.Msg.alert(respObj.status, respObj.message);
							if (respObj.status === 'success') {
								oms.admin.ProjectRoleEditPanel.hide();
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

oms.admin.createProjectRoleTypeAdminPanel=function()
{
	var store=Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId: 'projrolestore',
		fields: [
		{name: 'roletype' }, 
		{name: 'description'},
		{name:'createTS'}
		] 
		});
	var grid=Ext.create('Ext.grid.Panel',{
		id:'projrtadmingrid',
		store:store, 
		scrollable:true,
		tbar:[
			{
				text:"Create New Project Role" ,
				listeners:{
					click:
					{
						element:'el',
						fn:function(){
						
						Ext.getCmp('btprsave').setVisible(true);
						Ext.getCmp('btprdelete').setVisible(false);
						Ext.getCmp("projroleEditForm").getForm().reset();
						oms.admin.ProjectRoleEditPanel.show();
					}
				}
			}
			}
		],

		columns: [
		{text: "Role Type", dataIndex: 'roletype',width:220}, 
		{text: "Description", dataIndex: 'description',flex:1},
		{
            xtype:'actioncolumn',
            width:100,
            items: [{
                icon: 'css/images/shared/icons/fam/delete.gif',
                tooltip: 'Delete',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    Ext.getCmp("projroleEditForm").getForm().loadRecord(rec);
                    Ext.getCmp('btprsave').setVisible(false);
					Ext.getCmp('btprdelete').setVisible(true);
					oms.admin.ProjectRoleEditPanel.show();
                }
            }]
		}
		],
		columnLines: true, 
		title:'Project RoleTypes' 
		});
		return grid;
};