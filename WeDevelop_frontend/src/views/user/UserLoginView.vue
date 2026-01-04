<template>
  <div class="login-page">
    <div class="login-container">
      <a-card class="login-card" :bordered="false">
        <div class="card-header">
          <div class="logo-wrapper">
            <div class="logo-circle"></div>
          </div>
          <div class="title">用户登录</div>
          <div class="subtitle">欢迎回到 WeDevelop</div>
        </div>
        <a-form :model="form" :rules="rules" layout="vertical" @finish="onSubmit" class="login-form">
          <a-form-item label="账号" name="userAccount" required>
            <a-input 
              v-model:value="form.userAccount" 
              placeholder="请输入账号" 
              allow-clear 
              size="large"
              class="custom-input"
            />
          </a-form-item>
          <a-form-item label="密码" name="userPassword" required>
            <a-input-password 
              v-model:value="form.userPassword" 
              placeholder="请输入密码" 
              allow-clear 
              size="large"
              class="custom-input"
            />
          </a-form-item>

          <a-form-item>
            <a-space direction="vertical" style="width: 100%">
              <a-button 
                type="primary" 
                html-type="submit" 
                :loading="submitting" 
                block 
                size="large"
                class="login-button"
              >
                登录
              </a-button>
              <a-button type="link" block @click="toRegister" class="register-link">
                还没有账号？去注册
              </a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { userLogin, getLoginUser } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/loginUser.ts'


const router = useRouter()
const loginUserStore = useLoginUserStore()

const submitting = ref(false)


const form = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

const rules = {
  userAccount: [
    { required: true, message: '请输入账号' },
    { min: 4, message: '账号至少 4 位' },
  ],
  userPassword: [
    { required: true, message: '请输入密码' },
    { min: 6, message: '密码至少 6 位' },
  ],
}

const onSubmit = async () => {
  if (submitting.value) return
  
  
  submitting.value = true
  try {
    const res = await userLogin(form)
    if (res.data.code === 0) {
      // 登录成功后，刷新并写入全局登录用户
      const me = await getLoginUser()
      if (me.data.code === 0) {
        loginUserStore.setLoginUser(me.data.data)
      }
      message.success('登录成功')
      // 登录成功后总是跳转到首页
      router.replace('/')
    } else {
      message.error(res.data.message || '登录失败')

    }
  } finally {
    submitting.value = false
  }
}

const toRegister = () => {
  router.push('/user/register')
}
</script>

<style scoped>
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 64px - 88px);
  padding: 40px 20px;
  position: relative;
}

.login-container {
  width: 100%;
  max-width: 420px;
  position: relative;
  z-index: 1;
}

.login-card {
  width: 100%;
  border-radius: 20px;
  box-shadow: 
    0 10px 40px rgba(0, 0, 0, 0.08),
    0 2px 8px rgba(0, 0, 0, 0.04);
  overflow: hidden;
  background: #ffffff;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.login-card:hover {
  transform: translateY(-4px);
  box-shadow: 
    0 16px 48px rgba(59, 130, 246, 0.12),
    0 4px 12px rgba(0, 0, 0, 0.06);
}

.card-header {
  text-align: center;
  margin-bottom: 32px;
  padding-top: 8px;
}

.logo-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-circle {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 50%, #10b981 100%);
  position: relative;
  animation: logoPulse 3s ease-in-out infinite;
}

.logo-circle::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #ffffff;
}

.logo-circle::after {
  content: 'W';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
  font-weight: 700;
  background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 50%, #10b981 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

@keyframes logoPulse {
  0%, 100% {
    transform: scale(1);
    box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4);
  }
  50% {
    transform: scale(1.05);
    box-shadow: 0 0 0 8px rgba(59, 130, 246, 0);
  }
}

.title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
  background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 50%, #10b981 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: -0.5px;
}

.subtitle {
  font-size: 14px;
  color: #64748b;
  margin-top: 4px;
}

.login-form {
  margin-top: 8px;
}

:deep(.ant-form-item-label > label) {
  font-weight: 500;
  color: #334155;
  font-size: 14px;
}

.custom-input {
  border-radius: 10px;
  border: 1.5px solid #e2e8f0;
  transition: all 0.3s ease;
}

.custom-input:hover {
  border-color: #3b82f6;
}

:deep(.ant-input:focus),
:deep(.ant-input-focused),
:deep(.ant-input-password:focus),
:deep(.ant-input-password-focused) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.login-button {
  height: 48px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
  border: none;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
  transition: all 0.3s ease;
}

.login-button:hover {
  background: linear-gradient(135deg, #2563eb 0%, #7c3aed 100%);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
  transform: translateY(-2px);
}

.login-button:active {
  transform: translateY(0);
}

.register-link {
  color: #64748b;
  font-size: 14px;
  padding: 8px 0;
  transition: color 0.3s ease;
}

.register-link:hover {
  color: #3b82f6;
}

/* 响应式设计 */
@media (max-width: 480px) {
  .login-page {
    padding: 20px 16px;
  }

  .login-card {
    border-radius: 16px;
  }

  .title {
    font-size: 24px;
  }

  .logo-circle {
    width: 56px;
    height: 56px;
  }

  .logo-circle::after {
    font-size: 20px;
  }
}
</style>
