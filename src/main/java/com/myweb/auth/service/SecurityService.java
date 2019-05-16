//package com.myweb.auth.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import com.baiwangmaoyi.auth.constants.SecurityConstants;
//import com.baiwangmaoyi.auth.dao.UserTenantAccessDAO;
//import com.baiwangmaoyi.auth.dto.RolePermissionDTO;
//import com.baiwangmaoyi.auth.dto.TenantUserRoleDTO;
//import com.baiwangmaoyi.auth.entity.Permission;
//import com.baiwangmaoyi.auth.entity.UserTenantRole;
//import com.baiwangmaoyi.auth.dao.PermissionDAO;
//import com.baiwangmaoyi.auth.dao.RoleDAO;
//import com.baiwangmaoyi.auth.dao.RolePermissionDAO;
//import com.baiwangmaoyi.auth.dao.TokenkeyTenantAccessDAO;
//import com.baiwangmaoyi.auth.dao.UserTenantRoleDAO;
//import com.baiwangmaoyi.auth.dto.TenantRolePermissionDTO;
//import com.baiwangmaoyi.auth.dto.type.TokenkeyType;
//import com.baiwangmaoyi.auth.entity.Role;
//import com.baiwangmaoyi.auth.entity.RolePermission;
//import com.baiwangmaoyi.auth.entity.TokenkeyTenantAccess;
//import com.baiwangmaoyi.auth.entity.UserTenantAccess;
//import com.baiwangmaoyi.common.security.AccessType;
//import com.baiwangmaoyi.common.security.DefaultRole;
//import com.baiwangmaoyi.common.security.DefaultRoles;
//import com.baiwangmaoyi.common.security.DefaultTenants;
//import com.baiwangmaoyi.common.security.SimpleGrantedAuthority;
//import com.baiwangmaoyi.common.security.UserContext;
//import com.baiwangmaoyi.common.security.utils.UserContextUtils;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//
//@Service
//@Transactional
//public class SecurityService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);
//
//    @Autowired
//    private TokenkeyTenantAccessDAO tokenkeyTenantAccessDAO;
//
//    @Autowired
//    private PermissionDAO permissionDAO;
//
//    @Autowired
//    private UserTenantAccessDAO userTenantAccessDAO;
//
//    @Autowired
//    private RoleDAO roleDAO;
//
//    @Autowired
//    private RolePermissionDAO rolePermissionDAO;
//
//    @Autowired
//    private UserTenantRoleDAO userTenantRoleDAO;
//
////    @Autowired
////    private UrlProvider urlProvider;
//
//    public List<Permission> getPermissionListByTenantIdAndUserId(String userId, String tenantId) {
//        Map<String, UUID> map = new HashMap<>();
//        map.put("userId", UUID.fromString(userId));
//        map.put("tenantId", UUID.fromString(tenantId));
//        return permissionDAO.getPermissionList(map);
//    }
//
//    public List<UserTenantAccess> getUserTenantAccessList(String userId,String tenantId) {
//        Map<String, UUID> map = new HashMap<>();
//        map.put("userId", UUID.fromString(userId));
//        map.put("tenantId", UUID.fromString(tenantId));
//        return userTenantAccessDAO.selectByUserIdAndTenantId(map);
//    }
//
//    public void grantTenantUserRole(TenantUserRoleDTO tenantUserRoleDTO) {
//        UUID tenantId = tenantUserRoleDTO.getTenantId();
//        UUID userId = tenantUserRoleDTO.getUserId();
//        DefaultRole defaultRole = tenantUserRoleDTO.getRole().get(0);
//        afterUserCreated(userId, tenantId, defaultRole);
//
//    }
//
//    public void revokeRole(UUID userId) {
//        deleteAccessRole(userId);
//    }
//
//    public List<TenantRolePermissionDTO> getTenantRoles(UUID tenantId) {
//        List<TenantRolePermissionDTO> rolePermissionDTOList = getRolesByTenantId(tenantId);
//        return rolePermissionDTOList;
//    }
//
//    public List<RolePermissionDTO> getUserRoles(UUID userId) {
//        List<RolePermissionDTO> rolePermissionDTOList = getRolesByUserId(userId);
//        return rolePermissionDTOList;
//    }
//
//    public UUID generateAccessTokenKey(UUID tenantId, UUID documentId, Long expireSeconds, TokenkeyType type) {
//
//        String objectIds = "('" + documentId.toString() + "')";
//        String permissions = type.getPermissions();
//        Date expireTime = DateTime.now().plus(expireSeconds * 1000).toDate();
//
//        return generateTempAccessTokenKey(tenantId, SecurityConstants.FILTER_TYPE_DOCUMENT,
//                objectIds, permissions, expireTime);
//    }
//
//    private UUID generateTempAccessTokenKey(UUID tenantId, String objectType, String objectIds,
//                                            String permissions, Date expireTime) {
//        UUID tokenKey = UUID.randomUUID();
//        TokenkeyTenantAccess record = new TokenkeyTenantAccess();
//        record.setTenantId(tenantId);
//        record.setTokenKey(tokenKey);
//        record.setObjectType(objectType);
//        record.setObjectIds(objectIds);
//        record.setPermissions(permissions);
//        record.setExpireTime(expireTime);
//        tokenkeyTenantAccessDAO.insert(record);
//        return tokenKey;
//    }
//
//    public TokenkeyTenantAccess getActiveTokenkeyData(UUID tokenKey) {
//        return tokenkeyTenantAccessDAO.getActiveTokenkeyData(tokenKey);
//    }
//
//    public int cleanExpiredTokenkeys() {
//        return tokenkeyTenantAccessDAO.cleanExpiredTokenkeys();
//    }
//
//    public void setupUserContextForTokenkeyUser(UUID tokenKey, UUID tenantId) {
//        SecurityContextHolder.setContext(getUserContext(tokenKey, tenantId, true));
//    }
//
//    public void setupUserContextForNormalUser(UUID userId, UUID tenantId) {
//        SecurityContextHolder.setContext(getUserContext(userId, tenantId, false));
//    }
//
//    private void setupUserContext(UUID userId, UUID tenantId, boolean isTokenkeyUser) {
//        SecurityContextHolder.setContext(getUserContext(userId, tenantId, isTokenkeyUser));
//    }
//
//    public void refreshCurrentUserContext() {
//
//        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext() instanceof UserContext) {
//            UserContext context = (UserContext) SecurityContextHolder.getContext();
//            clearUserContextCache(context.getUserId(), context.getTenantId());
//            setupUserContext(context.getUserId(), context.getTenantId(), false);
//        }
//    }
//
//    /**
//     * Apply the access permission of the tenantId to the userId.
//     *
//     * @param userId user ID
//     * @param tenantId tenant ID
//     * @param accesslevel access level
//     */
//    public void addUserTenantAccess(UUID userId, UUID tenantId, Integer accesslevel) {
//
//        UserTenantAccess record = new UserTenantAccess();
//        record.setUserId(userId);
//        record.setTenantId(tenantId);
//        record.setAccessLevel(accesslevel);
//        userTenantAccessDAO.insert(record);
//
//    }
//
//    @Transactional
//    public void afterUserCreated(UUID userId, UUID tenantId, DefaultRole role) {
//
//        addUserTenantAccess(userId, tenantId, 0);
//        saveUserTenantRole(userId, tenantId, role);
//        clearUserContextCache(userId, tenantId);
//
//        addUserTenantAccess(UserContextUtils.getCurrentUserId(), tenantId, 0);
//
//        if (DefaultRoles.MASTER_ADMIN.getId().equals(role.getId())) {
//            addUserTenantAccess(userId, tenantId, 1);
//        }
//        refreshCurrentUserContext();
//    }
//
//    @Cacheable(cacheNames = "Security", key = "'UserContext:'.concat(#userId).concat(':').concat(#tenantId)")
//    public UserContext getUserContext(UUID userId, UUID tenantId, boolean isTokenkeyUser) {
//        if (isTokenkeyUser) {
//            return buildUserContextForTokenkey(userId, tenantId);
//        } else {
//            return buildUserContextForNormalUser(userId, tenantId);
//        }
//    }
//
//    //todo move to core
//    public static void runAs(UUID tenantId, Runnable runnable) {
//        UserContext userContext = new UserContext(DefaultTenants.ANONYMOUS_USER, tenantId);
//        Map<String, String> dataSecurityMap = new HashMap<>();
//        dataSecurityMap.put(SecurityConstants.FILTER_TYPE_TENANT, "('" + tenantId.toString() + "')");
//        userContext.setDataSecurityMap(dataSecurityMap);
//        userContext.setAccessType(AccessType.ACCESS_TYPE_NORMAL);
//        UserContextUtils.runAs(userContext, runnable);
//    }
//
//    private UserContext buildUserContextForTokenkey(UUID userId, UUID tenantId) {
//        UserContext userContext = new UserContext(userId, tenantId);
//        userContext.setAccessType(AccessType.ACCESS_TYPE_TOKENKEY);
//        Map<String, String> map = new HashMap<>();
//        TokenkeyTenantAccess record = tokenkeyTenantAccessDAO.getActiveTokenkeyData(userContext.getUserId());
//        map.put(record.getObjectType(), record.getObjectIds());
//        map.put(SecurityConstants.FILTER_TYPE_TENANT, "('" + record.getTenantId().toString() + "')");
//        userContext.setDataSecurityMap(map);
//
//        String[] permissions = record.getPermissions().split(",");
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority();
//            authority.setAuthority(permission);
//            authorityList.add(authority);
//        }
//        userContext.setAuthorities(authorityList);
//        return userContext;
//    }
//
//    private UserContext buildUserContextForNormalUser(UUID userId, UUID tenantId) {
//        UserContext userContext = new UserContext(userId, tenantId);
//        userContext.setAccessType(AccessType.ACCESS_TYPE_NORMAL);
//        Map<String, UUID> map = new HashMap<>();
//        map.put("userId", userContext.getUserId());
//        map.put("tenantId", userContext.getTenantId());
//        List<Permission> permissions = permissionDAO.getPermissionList(map);
//
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        for (Permission permission : permissions) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority();
//            authority.setAuthority(permission.getPermission());
//            authorityList.add(authority);
//        }
//        userContext.setAuthorities(authorityList);
//        userContext.setDataSecurityMap(generateTenantMap(userTenantAccessDAO.selectByUserIdAndTenantId(map)));
//        return userContext;
//    }
//
//
//    @CacheEvict(cacheNames = "Security", key = "'UserContext:'.concat(#userId).concat(':').concat(#tenantId)")
//    public void clearUserContextCache(UUID userId, UUID tenantId) {
//        LOGGER.info("cache evict for user {} tenant {}", userId, tenantId);
//    }
//
//    private Map generateTenantMap(List<UserTenantAccess> list) {
//
//        Map<String, String> map = new HashMap<>();
//        if (!CollectionUtils.isEmpty(list)) {
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("(");
//            boolean first = true;
//            for (UserTenantAccess o : list) {
//                buffer.append(first ? "" : ",");
//                buffer.append("'" + o.getTenantId().toString() + "'");
//                first = false;
//            }
//            buffer.append(")");
//            map.put(SecurityConstants.FILTER_TYPE_TENANT, buffer.toString());
//        }
//        return map;
//
//    }
//
//    public void saveUserTenantRole(UUID userId, UUID tenantId, DefaultRole role) {
//        UserTenantRole userTenantRole = new UserTenantRole.UserTenantRoleBuilder()
//                .withUserId(userId)
//                .withTenantId(tenantId)
//                .withRoleId(role.getId()).build();
//        userTenantRoleDAO.insertUtRole(userTenantRole);
//    }
//
//    public List<TenantRolePermissionDTO> getRolesByTenantId(UUID tId) {
//
//        List<Role> roleList = roleDAO.selectByTenantId(tId);
//        if (roleList == null) {
//            return null;
//        }
//        List<TenantRolePermissionDTO> result = new ArrayList<>();
//        for (int i = 0; i < roleList.size(); i++) {
//            TenantRolePermissionDTO tenantRolePermissionDTO = new TenantRolePermissionDTO();
//            tenantRolePermissionDTO.setRoleId(roleList.get(i).getId());
//            tenantRolePermissionDTO.setTenantId(roleList.get(i).getTenantId());
//            tenantRolePermissionDTO.setName(roleList.get(i).getName());
//            tenantRolePermissionDTO.setDescription(roleList.get(i).getDescription());
//            tenantRolePermissionDTO.setUpdateTime(roleList.get(i).getUpdateTime());
//            List<RolePermission> rolePermissionList = rolePermissionDAO.selectByRoleId(roleList.get(i).getId());
//            List<Permission> permissionList = new ArrayList<>();
//            for (int j = 0; j < rolePermissionList.size(); j++) {
//                UUID pId = rolePermissionList.get(j).getPermissionId();
//                permissionList.add(permissionDAO.selectByPrimaryKey(pId));
//            }
//            tenantRolePermissionDTO.setPermissions(permissionList);
//            result.add(tenantRolePermissionDTO);
//        }
//        return result;
//    }
//
//    public List<RolePermissionDTO> getRolesByUserId(UUID userId) {
//
//        List<UserTenantRole> utRoleList = userTenantRoleDAO.selectByUserId(userId);
//        List<Role> roleList = new ArrayList<>();
//        List<RolePermissionDTO> result = new ArrayList<>();
//        for (int i = 0; i < utRoleList.size(); i++) {
//            UUID roleId = utRoleList.get(i).getRoleId();
//            roleList.add(roleDAO.selectById(roleId));
//        }
//        //process can be optimized,may be in sql-view not multi queries in sql
//        for (int i = 0; i < roleList.size(); i++) {
//            RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
//            rolePermissionDTO.setRelationId(utRoleList.get(i).getId());
//            rolePermissionDTO.setRoleId(roleList.get(i).getId());
//            rolePermissionDTO.setTenantId(roleList.get(i).getTenantId());
//            rolePermissionDTO.setName(roleList.get(i).getName());
//            rolePermissionDTO.setDescription(roleList.get(i).getDescription());
//            rolePermissionDTO.setUpdateTime(roleList.get(i).getUpdateTime());
//            List<RolePermission> rolePermissionList = rolePermissionDAO.selectByRoleId(roleList.get(i).getId());
//            List<Permission> permissionList = new ArrayList<>();
//            for (int j = 0; j < rolePermissionList.size(); j++) {
//                UUID pId = rolePermissionList.get(j).getPermissionId();
//                permissionList.add(permissionDAO.selectByPrimaryKey(pId));
//            }
//            rolePermissionDTO.setPermissions(permissionList);
//            result.add(rolePermissionDTO);
//        }
//        return result;
//    }
//
//    public void deleteAccessRole(UUID userId) {
//        userTenantRoleDAO.deleteByUserId(userId);
//        userTenantAccessDAO.deleteByUserId(userId);
//    }
//
//    public boolean isAnonymousAccess(String token, long expireInMs) {
//        UserContext userContext = (UserContext) SecurityContextHolder.getContext();
//        return DefaultTenants.ANONYMOUS_TENANT.equals(userContext.getTenantId())
//                || (!org.springframework.util.StringUtils.isEmpty(token) && expireInMs > 0);
//    }
//}
