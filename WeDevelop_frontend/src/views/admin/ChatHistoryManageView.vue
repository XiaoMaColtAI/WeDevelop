<template>
  <div id="chatHistoryManagePage">
    <!-- 搜索表单 -->
    <a-form layout="inline" :model="searchParams" @finish="doSearch">
      <a-form-item label="消息内容">
        <a-input v-model:value="searchParams.message" placeholder="输入消息内容" />
      </a-form-item>
      <a-form-item label="消息类型">
        <a-select
          v-model:value="searchParams.messageType"
          placeholder="选择消息类型"
          style="width: 150px"
        >
          <a-select-option value="">全部</a-select-option>
          <a-select-option value="user">用户消息</a-select-option>
          <a-select-option value="ai">AI消息</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="应用ID">
        <a-input 
          v-model:value="searchParams.appId" 
          placeholder="输入应用ID" 
          type="number"
        />
      </a-form-item>
      <a-form-item label="用户ID">
        <a-input 
          v-model:value="searchParams.userId" 
          placeholder="输入用户ID" 
          type="number"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-divider />

    <!-- 表格 -->
    <a-table
      :columns="columns"
      :data-source="data"
      :pagination="pagination"
      @change="doTableChange"
      :scroll="{ x: 1200 }"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'id'">
          <span>{{ record.id }}</span>
        </template>
        <template v-else-if="column.dataIndex === 'message'">
          <a-tooltip :title="record.message">
            <div class="message-text">{{ record.message }}</div>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'messageType'">
          <a-tag v-if="record.messageType === 'user'" color="blue">用户消息</a-tag>
          <a-tag v-else-if="record.messageType === 'ai'" color="green">AI消息</a-tag>
          <span v-else>{{ record.messageType }}</span>
        </template>
        <template v-else-if="column.dataIndex === 'appId'">
          <a-button type="link" size="small" @click="viewApp(record.appId)">
            {{ record.appId }}
          </a-button>
        </template>
        <template v-else-if="column.dataIndex === 'userId'">
          <span>{{ record.userId }}</span>
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ formatTime(record.createTime) }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-button type="primary" size="small" @click="viewDetail(record)"> 详情 </a-button>
        </template>
      </template>
    </a-table>

    <!-- 详情弹窗 -->
    <a-modal 
      v-model:open="detailVisible" 
      title="对话历史详情" 
      width="900px" 
      :footer="null"
      class="chat-history-detail-modal"
    >
      <div v-if="selectedRecord" class="detail-content">
        <!-- 头部信息卡片 -->
        <div class="detail-header">
          <div class="header-item">
            <span class="item-label">ID</span>
            <span class="item-value">{{ selectedRecord.id }}</span>
          </div>
          <div class="header-item">
            <span class="item-label">消息类型</span>
            <a-tag v-if="selectedRecord.messageType === 'user'" color="blue" class="type-tag">
              用户消息
            </a-tag>
            <a-tag v-else-if="selectedRecord.messageType === 'ai'" color="green" class="type-tag">
              AI消息
            </a-tag>
          </div>
        </div>

        <!-- 消息内容区域 -->
        <div class="message-section">
          <div class="section-title">
            <span>消息内容</span>
          </div>
          <div class="message-content-wrapper" :class="{ 'ai-message': selectedRecord.messageType === 'ai', 'user-message': selectedRecord.messageType === 'user' }">
            <div class="message-content-inner">
              <MarkdownRenderer 
                v-if="selectedRecord.messageType === 'ai'" 
                :content="selectedRecord.message || ''" 
              />
              <pre v-else class="user-message-text">{{ selectedRecord.message }}</pre>
            </div>
          </div>
        </div>

        <!-- 关联信息 -->
        <div class="info-section">
          <div class="section-title">
            <span>关联信息</span>
          </div>
          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">应用ID</span>
              <a-button 
                type="link" 
                size="small" 
                @click="viewApp(selectedRecord.appId)"
                class="info-link"
              >
                {{ selectedRecord.appId }}
              </a-button>
            </div>
            <div class="info-item">
              <span class="info-label">用户ID</span>
              <span class="info-value">{{ selectedRecord.userId }}</span>
            </div>
          </div>
        </div>

        <!-- 时间信息 -->
        <div class="time-section">
          <div class="section-title">
            <span>时间信息</span>
          </div>
          <div class="time-grid">
            <div class="time-item">
              <span class="time-label">创建时间</span>
              <span class="time-value">{{ formatTime(selectedRecord.createTime) }}</span>
            </div>
            <div class="time-item">
              <span class="time-label">更新时间</span>
              <span class="time-value">{{ formatTime(selectedRecord.updateTime) }}</span>
            </div>
          </div>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  listAllChatHistoryByPageForAdmin,
} from '@/api/chatHistoryController'
import { formatTime } from '@/utils/time'
import MarkdownRenderer from '@/components/MarkdownRender.vue'

const router = useRouter()

const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    width: 80,
    fixed: 'left',
  },
  {
    title: '消息类型',
    dataIndex: 'messageType',
    width: 100,
  },
  {
    title: '消息内容',
    dataIndex: 'message',
    width: 300,
  },
  {
    title: '应用ID',
    dataIndex: 'appId',
    width: 100,
  },
  {
    title: '用户ID',
    dataIndex: 'userId',
    width: 100,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 180,
  },
  {
    title: '操作',
    key: 'action',
    width: 150,
    fixed: 'right',
  },
]

// 数据
const data = ref<API.ChatHistory[]>([])
const total = ref(0)

// 搜索条件
const searchParams = reactive<API.ChatHistoryQueryRequest>({
  pageNum: 1,
  pageSize: 10,
  sortField: 'createTime',
  sortOrder: 'descend',
  message: undefined,
  messageType: undefined,
  appId: undefined,
  userId: undefined,
})

// 详情弹窗
const detailVisible = ref(false)
const selectedRecord = ref<API.ChatHistory | null>(null)

// 获取数据
const fetchData = async () => {
  try {
    // 构建查询参数，将空字符串转换为 undefined，避免后端将其作为查询条件
    const queryParams: API.ChatHistoryQueryRequest = {
      pageNum: searchParams.pageNum,
      pageSize: searchParams.pageSize,
      sortField: searchParams.sortField,
      sortOrder: searchParams.sortOrder,
      // 只有非空值才添加到查询参数中
      message: searchParams.message?.trim() || undefined,
      messageType: searchParams.messageType?.trim() || undefined,
      // 对于数字类型，需要确保是有效的数字
      appId: searchParams.appId && !isNaN(Number(searchParams.appId)) 
        ? Number(searchParams.appId) 
        : undefined,
      userId: searchParams.userId && !isNaN(Number(searchParams.userId)) 
        ? Number(searchParams.userId) 
        : undefined,
    }
    
    const res = await listAllChatHistoryByPageForAdmin(queryParams)
    if (res.data.data) {
      data.value = res.data.data.records ?? []
      total.value = res.data.data.totalRow ?? 0
    } else {
      message.error('获取数据失败，' + res.data.message)
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    message.error('获取数据失败')
  }
}

// 页面加载时请求一次
onMounted(() => {
  fetchData()
})

// 分页参数
const pagination = computed(() => {
  return {
    current: searchParams.pageNum ?? 1,
    pageSize: searchParams.pageSize ?? 10,
    total: total.value,
    showSizeChanger: true,
    showTotal: (total: number) => `共 ${total} 条`,
  }
})

// 表格变化处理
const doTableChange = (page: { current: number; pageSize: number }) => {
  searchParams.pageNum = page.current
  searchParams.pageSize = page.pageSize
  fetchData()
}

// 搜索
const doSearch = () => {
  // 重置页码
  searchParams.pageNum = 1
  fetchData()
}

// 查看详情
const viewDetail = (record: API.ChatHistory) => {
  selectedRecord.value = record
  detailVisible.value = true
}

// 查看应用
const viewApp = (appId: number | undefined) => {
  if (appId) {
    router.push(`/app/chat/${appId}`)
  }
}
</script>

<style scoped>
#chatHistoryManagePage {
  padding: 24px;
  background: white;
  margin-top: 16px;
}

.message-text {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.message-detail {
  max-height: 500px;
  overflow-y: auto;
}

.user-message-detail {
  white-space: pre-wrap;
  word-wrap: break-word;
  margin: 0;
  padding: 12px;
  background: #f5f5f5;
  border-radius: 4px;
}

:deep(.ant-table-tbody > tr > td) {
  vertical-align: middle;
}

/* 详情弹窗样式 */
.chat-history-detail-modal :deep(.ant-modal-content) {
  border-radius: 12px;
  overflow: hidden;
}

.chat-history-detail-modal :deep(.ant-modal-header) {
  padding: 20px 24px;
  border-bottom: 1px solid #f0f0f0;
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
}

.chat-history-detail-modal :deep(.ant-modal-title) {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a1a;
}

.chat-history-detail-modal :deep(.ant-modal-body) {
  padding: 0;
}

.detail-content {
  display: flex;
  flex-direction: column;
  gap: 0;
}

/* 头部信息卡片 */
.detail-header {
  display: flex;
  gap: 24px;
  padding: 20px 24px;
  background: #fafafa;
  border-bottom: 1px solid #f0f0f0;
}

.header-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.header-item .item-label {
  font-size: 12px;
  color: #8c8c8c;
  font-weight: 500;
}

.header-item .item-value {
  font-size: 16px;
  color: #1a1a1a;
  font-weight: 600;
}

.type-tag {
  font-size: 13px;
  padding: 4px 12px;
  border-radius: 12px;
  font-weight: 500;
}

/* 消息内容区域 */
.message-section {
  padding: 24px;
  border-bottom: 1px solid #f0f0f0;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: #595959;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #f0f0f0;
}

.message-content-wrapper {
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #e8e8e8;
}

.message-content-wrapper.ai-message {
  background: #f6ffed;
  border-color: #b7eb8f;
}

.message-content-wrapper.user-message {
  background: #e6f7ff;
  border-color: #91d5ff;
}

.message-content-inner {
  max-height: 500px;
  overflow-y: auto;
  padding: 20px;
}

.message-content-inner :deep(.markdown-body) {
  background: transparent;
  padding: 0;
}

.message-content-inner :deep(.markdown-body pre) {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 6px;
  padding: 12px;
  margin: 12px 0;
}

.message-content-inner :deep(.markdown-body code) {
  background: rgba(0, 0, 0, 0.05);
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 13px;
}

.user-message-text {
  white-space: pre-wrap;
  word-wrap: break-word;
  margin: 0;
  padding: 0;
  font-size: 14px;
  line-height: 1.8;
  color: #1a1a1a;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

/* 关联信息区域 */
.info-section {
  padding: 24px;
  border-bottom: 1px solid #f0f0f0;
  background: #fafafa;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-label {
  font-size: 12px;
  color: #8c8c8c;
  font-weight: 500;
}

.info-value {
  font-size: 15px;
  color: #1a1a1a;
  font-weight: 500;
}

.info-link {
  padding: 0;
  height: auto;
  font-size: 15px;
  font-weight: 500;
}

/* 时间信息区域 */
.time-section {
  padding: 24px;
  background: #ffffff;
}

.time-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.time-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.time-label {
  font-size: 12px;
  color: #8c8c8c;
  font-weight: 500;
}

.time-value {
  font-size: 15px;
  color: #1a1a1a;
  font-weight: 500;
}

/* 滚动条样式 */
.message-content-inner::-webkit-scrollbar {
  width: 6px;
}

.message-content-inner::-webkit-scrollbar-track {
  background: transparent;
}

.message-content-inner::-webkit-scrollbar-thumb {
  background: #d9d9d9;
  border-radius: 3px;
}

.message-content-inner::-webkit-scrollbar-thumb:hover {
  background: #bfbfbf;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-history-detail-modal :deep(.ant-modal) {
    width: 95% !important;
    max-width: 95%;
  }

  .detail-header {
    flex-direction: column;
    gap: 16px;
  }

  .info-grid,
  .time-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .message-section,
  .info-section,
  .time-section {
    padding: 16px;
  }
}
</style>

