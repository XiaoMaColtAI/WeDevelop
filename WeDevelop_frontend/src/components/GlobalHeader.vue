<template>
  <a-layout-header class="global-header">
    <div class="header-content">
      <!-- 左侧 Logo 和标题 -->
      <div class="header-left">
        <div class="logo-container" @click="goToHome">
          <img src="@/assets/logo.png" alt="Logo" class="logo" />
          <span class="site-title">WeDevelop</span>
        </div>

        <!-- 桌面端导航菜单 -->
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="horizontal"
          :items="menuItems"
          @click="handleMenuClick"
          class="header-menu desktop-menu"
        />
      </div>

      <!-- 右侧用户信息 / 登录 -->
      <div class="header-right">
        <!-- 移动端汉堡菜单按钮 -->
        <a-button 
          type="text" 
          class="mobile-menu-btn"
          @click="toggleMobileMenu"
        >
          <template #icon>
            <MenuOutlined v-if="!mobileMenuVisible" />
            <CloseOutlined v-else />
          </template>
        </a-button>

        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.id">
            <a-dropdown placement="bottomRight">
              <a-space>
                <a-avatar :src="loginUserStore.loginUser.userAvatar" />
                <span class="user-name">{{ loginUserStore.loginUser.userName ?? '无名' }}</span>
              </a-space>
              <template #overlay>
                <a-menu @click="handleUserMenuClick">
                  <a-menu-item key="profile">个人设置</a-menu-item>
                  <a-menu-divider />
                  <a-menu-item key="logout">退出登录</a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div v-else>
            <a-button type="primary" href="/user/login">登录</a-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 移动端抽屉菜单 -->
    <a-drawer
      v-model:open="mobileMenuVisible"
      placement="right"
      :closable="false"
      :width="280"
      class="mobile-drawer"
    >
      <div class="mobile-menu-header">
        <div class="logo-container" @click="goToHome; mobileMenuVisible = false">
          <img src="@/assets/logo.png" alt="Logo" class="logo" />
          <span class="site-title">WeDevelop</span>
        </div>
        <a-button type="text" @click="mobileMenuVisible = false">
          <template #icon>
            <CloseOutlined />
          </template>
        </a-button>
      </div>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="vertical"
        :items="menuItems"
        @click="handleMobileMenuClick"
        class="mobile-menu"
      />
    </a-drawer>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import type { MenuProps } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { userLogout } from '@/api/userController.ts'
import checkAccess from '@/access/checkAccess'
import { MenuOutlined, CloseOutlined } from '@ant-design/icons-vue'

const router = useRouter()

// 移动端菜单状态
const mobileMenuVisible = ref(false)

// 切换移动端菜单
const toggleMobileMenu = () => {
  mobileMenuVisible.value = !mobileMenuVisible.value
}

// 当前选中的菜单项
const selectedKeys = ref<string[]>(['/'])
// 监听路由变化，更新当前选中菜单
router.afterEach((to) => {
  selectedKeys.value = [to.path]
})

const loginUserStore = useLoginUserStore()

// 原始菜单配置（基于路由）
const allMenus = [
  {
    key: '/',
    label: '首页',
    path: '/',
  },
  {
    key: '/admin/appManage',
    label: '应用管理',
    path: '/admin/appManage',
    requiredRole: 'admin',
  },
  {
    key: '/admin/chatHistoryManage',
    label: '对话管理',
    path: '/admin/chatHistoryManage',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    path: '/admin/userManage',
    requiredRole: 'admin',
  },
]

// 根据权限过滤菜单项
const menuItems = computed(() => {
  return allMenus.filter((menu) => {
    // 从路由配置中找到对应的路由
    const route = router.getRoutes().find(route => route.path === menu.path)
    if (!route) return false
    
    // 如果路由配置了 hideInMenu，则隐藏
    if (route.meta?.hideInMenu) {
      return false
    }
    
    // 检查菜单是否需要特定角色
    if (menu.requiredRole === 'admin') {
      // 只有管理员才能看到
      return loginUserStore.loginUser.userRole === 'admin'
    }
    
    // 根据权限过滤菜单，有权限则返回 true，则保留该菜单
    return checkAccess(loginUserStore.loginUser, route.meta?.access as string)
  }).map(menu => ({
    key: menu.key,
    label: menu.label,
  }))
})

// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  // 跳转到对应页面
  router.push(key)
}

// 处理移动端菜单点击
const handleMobileMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  // 关闭抽屉
  mobileMenuVisible.value = false
  // 跳转到对应页面
  router.push(key)
}

// 点击 Logo 跳转到首页
const goToHome = () => {
  router.push('/')
}

// 处理用户菜单点击
const handleUserMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  if (key === 'profile') {
    router.push('/user/profile')
  } else if (key === 'logout') {
    handleLogout()
  }
}

// 注销
const handleLogout = async () => {
  await userLogout()
  loginUserStore.setLoginUser({ userName: '未登录' } as any)
  router.replace('/user/login')
}
</script>

<style scoped>
.global-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  z-index: 1000;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0;
  height: 64px;
  line-height: 64px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  max-width: 100%;
  margin: 0 auto;
  padding: 0 24px;
}

.header-left {
  display: flex;
  align-items: center;
  flex: 1;
}

.logo-container {
  display: flex;
  align-items: center;
  margin-right: 40px;
  cursor: pointer;
  transition: opacity 0.2s;
}

.logo-container:hover {
  opacity: 0.8;
}

.logo {
  height: 32px;
  width: 32px;
  margin-right: 12px;
}

.site-title {
  font-size: 20px;
  font-weight: 600;
  color: #1890ff;
  white-space: nowrap;
}

.header-menu {
  border-bottom: none;
  background: transparent;
  flex: 1;
  min-width: 0;
}

/* 防止菜单折叠 */
:deep(.ant-menu-overflow) {
  display: flex !important;
}

:deep(.ant-menu-overflow-item) {
  flex-shrink: 0;
}

:deep(.ant-menu-overflow-item-rest) {
  display: none !important;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-login-status {
  display: flex;
  align-items: center;
}

/* 移动端汉堡菜单按钮 - 默认隐藏 */
.mobile-menu-btn {
  display: none;
  font-size: 20px;
  margin-right: 12px;
}

/* 移动端抽屉菜单头部 */
.mobile-menu-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 8px;
}

.mobile-menu-header .logo-container {
  margin-right: 0;
}

/* 移动端菜单样式 */
.mobile-menu {
  border-right: none !important;
}

.mobile-menu :deep(.ant-menu-item) {
  height: 48px;
  line-height: 48px;
  font-size: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    padding: 0 16px;
  }

  .logo-container {
    margin-right: 20px;
  }

  .site-title {
    font-size: 18px;
  }

  /* 隐藏桌面端菜单，显示移动端按钮 */
  .desktop-menu {
    display: none !important;
  }

  .mobile-menu-btn {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  /* 用户名在移动端隐藏，只显示头像 */
  .user-name {
    display: none;
  }
}

@media (max-width: 480px) {
  .header-content {
    padding: 0 12px;
  }

  .logo-container {
    margin-right: 12px;
  }

  .site-title {
    font-size: 16px;
  }
}
</style>
