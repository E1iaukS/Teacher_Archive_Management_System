import axios from 'axios'
import { useAuthStore } from '@/store/auth'

const instance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
})

instance.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

instance.interceptors.response.use(
  (resp) => {
    return resp.data.data ?? resp.data
  },
  (error) => {
    const message = error.response?.data?.message || error.message
    window.alert(message)
    return Promise.reject(error)
  }
)

export default instance
