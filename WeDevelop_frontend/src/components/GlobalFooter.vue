<template>
  <a-layout-footer class="global-footer">
    <div class="footer-content">
      <p class="copyright">
        © 2025 Developed By SeeWorld
      </p>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
// 这个组件不需要任何逻辑，只是展示版权信息
</script>

<style scoped>
.global-footer {
  background: #f0f2f5;
  border-top: 1px solid #e8e8e8;
  padding: 24px 0;
  text-align: center;
  position: relative;
  bottom: 0;
  width: 100%;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.copyright {
  margin: 0;
  color: #666;
  font-size: 14px;
  line-height: 1.5;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    padding: 0 16px;
  }

  .copyright {
    font-size: 13px;
  }
}

@media (max-width: 480px) {
  .global-footer {
    padding: 16px 0;
  }

  .footer-content {
    padding: 0 12px;
  }

  .copyright {
    font-size: 12px;
  }
}
</style>
