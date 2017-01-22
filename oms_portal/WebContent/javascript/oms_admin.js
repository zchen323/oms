// this is the admin js
// Doctype Editing / Search type Editing / User Role editing /
// Task Template Editing

oms.admin.createRoleAdminPanel = function() {
	var rolestore = Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId : 'roleStore',
		fields : [ {
			name : 'role'
		}, {
			name : 'fullaccess',
			type : 'boolean'
		} ]
	});

var grid=Ext.create('Ext.grid.Panel',{
																								id  : 'projrolegrid',
						store : rolestore,
						scrollable : true,
						tbar : [ {
							text : "Add New Role",
							listeners : {
								click : {
									element : 'el',
									fn : function() {
										oms.admin.userEditPanel.show();
									}
								}
							}
						} ],

						columns : [

								{
									text : "Role Name",
									dataIndex : 'role'
								},
								{
									text : "Restricted Access",
									dataIndex : 'fullaccess'
								},
								{
									text : "Action",
									dataIndex : "taskID",
									width : 60,
									renderer : function(val) {
										var html = '<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" >';
										return html;

									}
								} ],
						columnLines : true,
						title : 'Roles'
					});
	return grid;
};
oms.admin.createUserAdminPanel = function() {
	var ustore = Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId : 'ulistStore',
		fields : [ {
			name : 'userID'
		}, {
			name : 'username'
		}, {
			name : 'name'
		}, {
			name : 'email'
		}, {
			name : 'role'
		}, {
			name : 'fullaccess',
			type : 'boolean'
		}, {
			name : "createdTS",
			type : 'date'
		} ]
	});
var grid=Ext.create('Ext.grid.Panel',{
																								id  : 'userlistadmingrid',
						store : ustore,
						scrollable : true,
						tbar : [ {
							text : "Add New User",
							listeners : {
								click : {
									element : 'el',
									fn : function() {
										oms.admin.userEditPanel.show();
									}
								}
							}
						} ],

						columns : [
								{
									text : "Restricted Access",
									dataIndex : 'fullaccess',
									width : 160,
									renderer : function(val) {
										if (val == true) {
											return "<img src='css/images/sec.jpg' width=20 />";
										}
										return "";
									}
								},
								{
									text : "Username",
									flex : 2,
									dataIndex : 'username',
									renderer : function(val) {
										if (val == null) {
											return "<a href='#'>Upload Now</a>";
										} else {
											return val;
										}

									}
								},
								{
									text : "Name",
									dataIndex : 'name',
									width : 180
								},
								{
									text : "Created",
									dataIndex : 'createdTS',
									formatter : 'date("m/d/Y")'
									//renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
								},
								{
									text : "Email",
									dataIndex : 'email',
									width : 160
								},
								{
									text : "Role.",
									dataIndex : "role",
									width : 120,
									renderer : function(val) {
										if (val == "Y") {
											return '<font color=red>' + val
													+ '</font>';
										}
										return val;
									}
								},
								{
									text : "Action",
									dataIndex : "taskID",
									width : 60,
									renderer : function(val) {
										var html = '<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/user_delete.png" >';
										return html;

									}
								} ],
						columnLines : true,
						title : 'Users'
					});
	return grid;
};

oms.admin.createAdminPanel = function() {
	var ugrid = oms.admin.createUserAdminPanel();
	var dtgrid = oms.admin.createDoctypeAdminPanel();
	// var stgrid=oms.admin.createSearchTypeAdminPanel();
	var panel = Ext.create('Ext.panel.Panel', {
		id : "adminMain",
		layout : 'vbox',
		title : "OMS Admin",
		border : true,
		items : [ {
			xtype : 'tabpanel',
			id : 'adminDetails',
			width : '99%',
			margin : '2 2 2 2',
			flex : 1,
			defaults : {
				bodypadding : 10,
				scrollable : true,
				border : true
			},
			// tabPosition: 'right',
			items : [ dtgrid,// doc type
			oms.admin.createProjectRoleTypeAdminPanel(), ugrid,// user
			oms.admin.createTLPanel(), // tasktemplate
			oms.admin.createPLPanel() // project template
			// oms.admin.userEditPanel
			]
		} ]
	});
	return panel;
};

oms.admin.userEditPanel = Ext.create('Ext.window.Window', {
	frame : true,
	float : true,
	closable : true,
	title : 'Edit User',
	bodyPadding : 10,
	scrollable : true,
	closeAction : 'hide',
	width : 360,
	modal : false,
	defaultType : 'textfield',
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth : 150,
		msgTarget : 'side'
	},
	items : [ {
		fieldLabel : 'User Name',
		name : 'user',
		emptyText : 'user id'
	}, {
		fieldLabel : 'Password',
		name : 'pass',
		emptyText : 'password',
		inputType : 'password'
	}, {
		fieldLabel : 'Verify',
		name : 'verify',
		emptyText : 'password',
		inputType : 'password'
	}, {
		fieldLabel : 'Name',
		emptyText : 'Name',
		name : 'name'
	}, {
		fieldLabel : 'Company',
		name : 'company'
	}, {
		fieldLabel : 'Email',
		name : 'email',
		vtype : 'email'
	}, {
		fieldLabel : 'is Contractor',
		xtype : 'checkbox',
		name : 'iscontractor'
	}, {
		fieldLabel : 'Role',
		name : 'role'
	}, {
		fieldLabel : 'Restr. Access',
		xtype : 'checkbox',
		name : 'fullaccess'
	} ],
	buttons : [ {
		text : 'Save',
		disabled : false,
		formBind : true,
		handler : function() {
			console.log("create new user");
			var items = this.up('window').items.items;
			console.log(items);
			var formdata = {};
			for(var i = 0; i < items.length; i++){
				formdata[[items[i].name]] = items[i].value;
			}
			console.log(formdata);
			// ajax call backend to create new user
			Ext.Ajax.request({
				url: "api/user",
				method: 'POST',
				jsonData: JSON.stringify(formdata),
				success: function(response, option){
					console.log(response);
					var respObj = Ext.decode(response.responseText);
					Ext.Msg.alert(respObj.status, respObj.message);
					if(respObj.status === 'success'){
						oms.admin.userEditPanel.hide();
					}
				},
				failure: function(response, option){
					console.log(response);
					Ext.Msg.alert('Error', response.responseText);
				}
			});
		}
	} ]

});

oms.admin.createSearchTypeAdminPanel = function() {
	var ststore = Ext.create('Ext.data.JsonStore', {
		storeId : 'ststore',
		fields : [ {
			name : 'id'
		}, {
			name : 'searchname'
		}, {
			name : 'type'
		} ]
	});

var grid=Ext.create('Ext.grid.Panel',{
																								id  : 'searchtypegrid',
						store : ststore,
						scrollable : true,
						tbar : [ {
							text : "Add New Search",
							listeners : {
								click : {
									element : 'el',
									fn : function() {
										oms.admin.userEditPanel.show();
									}
								}
							}
						} ],
						columns : [
								{
									text : "Search ID",
									dataIndex : 'id',
									width : 120
								},
								{
									text : "Search Name",
									dataIndex : 'searchname',
									flex : 1
								},
								{
									text : "Type",
									dataIndex : 'type',
									width : 200
								},
								{
									text : "Action",
									width : 60,
									index : 'id',
									renderer : function(val) {
										var html = '<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/delete.gif" >';
										return html;

									}
								} ],
						columnLines : true,
						title : 'Search Types'
					});
	return grid;
};
oms.admin.createDoctypeAdminPanel = function() {
	var dtstore = Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId : 'dtStore',
		fields : [ {
			name : 'id'
		}, {
			name : 'doctype'
		}, {
			name : 'description'
		}, {
			name : 'sampleURL'
		}, {
			name : 'createTS'
		} ]
	});
var grid=Ext.create('Ext.grid.Panel',{
																								id  : 'dtadminngrid',
						store : dtstore,
						scrollable : true,
						tbar : [ {
							text : "Add New DocType",
							listeners : {
								click : {
									element : 'el',
									fn : function() {
										oms.admin.userEditPanel.show();
									}
								}
							}
						} ],

						columns : [

								{
									text : "Doctype ID",
									dataIndex : 'id',
									width : 120
								},
								{
									text : "Doc Type",
									dataIndex : 'doctype',
									width : 180
								},
								{
									text : "Description",
									dataIndex : 'description'
								},
								{
									text : "Sample URL",
									dataIndex : 'sampleURL',
									flex : 1
								},
								{
									text : "Action",
									dataIndex : "id",
									width : 60,
									renderer : function(val) {
										var html = '<img src="css/images/shared/icons/fam/user_edit.png"><img src="css/images/shared/icons/fam/delete.gif" >';
										return html;

									}
								} ],
						columnLines : true,
						title : 'Doctypes'
					});
	return grid;
};

oms.admin.createProjectRoleTypeAdminPanel = function() {
	var store = Ext.create('Ext.data.JsonStore', {
		// store configs
		storeId : 'projrolestore',
		fields : [ {
			name : 'roletype'
		}, {
			name : 'description'
		}, {
			name : 'createTS'
		} ]
	});
	var grid = Ext
			.create(
					'Ext.grid.Panel',
					{
						id : 'projrtadmingrid',
						store : store,
						scrollable : true,
						tbar : [ {
							text : "New Project Role",
							listeners : {
								click : {
									element : 'el',
									fn : function() {
										alert("not implemented");
									}
								}
							}
						} ],

						columns : [
								{
									text : "Doc Type",
									dataIndex : 'roletype',
									width : 220
								},
								{
									text : "Description",
									dataIndex : 'description',
									flex : 1
								},
								{
									text : "Action",
									dataIndex : "roletype",
									width : 60,
									renderer : function(val) {
										var html = '<img src="css/images/shared/icons/fam/delete.gif" >';
										return html;

									}
								} ],
						columnLines : true,
						title : 'Project RoleTypes'
					});
	return grid;
};