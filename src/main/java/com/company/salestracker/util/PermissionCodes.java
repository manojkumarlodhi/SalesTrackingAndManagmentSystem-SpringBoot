package com.company.salestracker.util;

public class PermissionCodes {
	// SUPER_ADMIN Permissions
    public static final String READ_ONLY = "READ_ONLY";
    public static final String ALL_VIEW = "ALL_VIEW";
    public static final String CREATE_ADMIN = "CREATE_ADMIN";
    public static final String CREATE_SUPER_ADMIN = "CREATE_SUPER_ADMIN";
    public static final String CREATE_MANAGER = "CREATE_MANAGER";
    public static final String DELETE = "DELETE";
    public static final String CREATE_SALES = "CREATE_SALES";
    public static final String UPDATE = "UPDATE";
    
    // ADMIN Permissions
    public static final String MANAGE_USERS = "MANAGE_USERS";
    public static final String VIEW_REPORTS = "VIEW_REPORTS";
    
    // MANAGER Permissions
    public static final String ASSIGN_TARGETS = "ASSIGN_TARGETS";
    public static final String VIEW_TEAM = "VIEW_TEAM";
    
    // EXECUTIVE Permissions
    public static final String CREATE_LEAD = "CREATE_LEAD";
    public static final String UPDATE_LEAD = "UPDATE_LEAD";
    public static final String CREATE_DEAL = "CREATE_DEAL";
    public static final String RECORD_SALE = "RECORD_SALE";

}
