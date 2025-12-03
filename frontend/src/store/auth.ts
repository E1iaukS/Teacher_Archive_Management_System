import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api/auth'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(sessionStorage.getItem('token'))
  const user = ref<any>(null)

  const setToken = (value: string) => {
    token.value = value
    sessionStorage.setItem('token', value)
  }

  const login = async (username: string, password: string) => {
    const res = await loginApi({ username, password })
    setToken(res.token)
    user.value = res
  }

  const logout = () => {
    token.value = null
    sessionStorage.removeItem('token')
    user.value = null
  }

  return { token, user, login, logout }
})
