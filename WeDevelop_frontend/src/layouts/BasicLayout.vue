<template>
  <a-layout class="basic-layout">
    <!-- 顶部导航栏 -->
    <GlobalHeader />

    <!-- 主要内容区域 -->
    <a-layout-content class="main-content">
      <div class="content-wrapper">
        <router-view />
      </div>
    </a-layout-content>

    <!-- 底部版权信息 -->
    <GlobalFooter v-if="!$route.path.startsWith('/app/')" />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  background-image: linear-gradient(135deg, #fdfcfb 0%, #e2d1c3 100%);
  padding: 24px;
  padding-top: 88px; /* 64px header + 24px padding */
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  flex: 1;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  min-height: calc(100vh - 64px - 88px); /* 减去 header 和 footer 的高度 */
}

/* 响应式设计 */
@media (max-width: 2000px) {
  .content-wrapper {
    max-width: 100%;
    margin: 0;
  }
}

@media (max-width: 768px) {
  .main-content {
    padding: 16px;
    padding-top: 80px; /* 64px header + 16px padding */
  }

  .content-wrapper {
    padding: 16px;
    border-radius: 4px;
  }
}

@media (max-width: 480px) {
  .main-content {
    padding: 12px;
    padding-top: 76px; /* 64px header + 12px padding */
  }

  .content-wrapper {
    padding: 12px;
    border-radius: 4px;
  }
}
</style>
