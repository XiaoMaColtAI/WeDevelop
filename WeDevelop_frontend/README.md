# WeDevelop Frontend

WeDevelop AI 应用开发平台的前端项目，基于 Vue 3 + TypeScript + Ant Design Vue 构建。

## 项目简介

WeDevelop 是一个 AI 驱动的应用开发平台，用户可以通过自然语言对话的方式快速创建和部署 Web 应用。平台提供完整的应用生命周期管理，包括创建、编辑、部署和运行。

## 技术栈

- **框架**: Vue 3.5+ (Composition API)
- **开发语言**: TypeScript 5.8+
- **UI 组件库**: Ant Design Vue 4.2+
- **状态管理**: Pinia 3.0+
- **路由**: Vue Router 4.5+
- **HTTP 客户端**: Axios
- **构建工具**: Vite 7.0+
- **代码规范**: ESLint + Prettier
- **API 生成**: OpenAPI TypeScript

## 项目结构

```
WeDevelop_frontend/
├── src/
│   ├── access/           # 权限控制模块
│   ├── api/              # API 接口定义
│   ├── components/       # 公共组件
│   ├── config/           # 配置文件
│   ├── layouts/          # 布局组件
│   ├── router/           # 路由配置
│   ├── stores/           # Pinia 状态管理
│   ├── utils/            # 工具函数
│   ├── views/            # 页面组件
│   ├── App.vue           # 根组件
│   ├── main.ts           # 入口文件
│   └── request.ts        # 请求封装
├── public/               # 静态资源
└── package.json          # 项目配置
```

## 主要功能

### 用户功能
- 用户注册/登录
- AI 对话创建应用
- 应用编辑与代码管理
- 应用部署与运行
- 个人信息管理

### 管理员功能
- 用户管理（查看、编辑用户信息）
- 应用管理（查看、编辑所有应用）
- 设置精选应用

### 权限控制
- 基于角色的访问控制（RBAC）
- 路由守卫与权限校验
- 管理员专属操作权限

## 环境配置

在项目根目录创建 `.env.development` 文件：

```env
# 服务端接口地址
VITE_SERVE=http://localhost:8101
```

## 开发指南

### 安装依赖

```sh
npm install
```

### 启动开发服务器

```sh
npm run dev
```

访问 http://localhost:5173

### 类型检查

```sh
npm run type-check
```

### 构建生产版本

```sh
npm run build
```

### 代码格式化

```sh
npm run format
```

### 代码检查

```sh
npm run lint
```

### API 类型生成

```sh
npm run openapi2ts
```

## 核心组件说明

| 组件 | 功能 |
|------|------|
| `GlobalHeader.vue` | 全局头部导航 |
| `AppCard.vue` | 应用卡片展示 |
| `AppDetailModal.vue` | 应用详情弹窗 |
| `DeploySuccessModal.vue` | 部署成功提示 |
| `MarkdownRender.vue` | Markdown 内容渲染 |
| `UserInfo.vue` | 用户信息展示 |

## 核心页面说明

| 页面 | 路径 | 功能 |
|------|------|------|
| 首页 | `/` | 应用展示与搜索 |
| 登录 | `/user/login` | 用户登录 |
| 注册 | `/user/register` | 用户注册 |
| 应用对话 | `/app/chat` | AI 对话创建应用 |
| 应用编辑 | `/app/edit/:id` | 应用编辑 |
| 用户管理 | `/admin/user` | 管理员-用户管理 |
| 应用管理 | `/admin/app` | 管理员-应用管理 |

## 推荐开发环境

- **IDE**: [VSCode](https://code.visualstudio.com/)
- **插件**: [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (需禁用 Vetur)

## 相关文档

- [Vite 配置参考](https://vite.dev/config/)
- [Vue 3 文档](https://cn.vuejs.org/)
- [Ant Design Vue 文档](https://antdv.com/)
- [TypeScript 文档](https://www.typescriptlang.org/)
