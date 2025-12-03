import request from '@/utils/request'

export const login = (data: { username: string; password: string }) => {
  return request.post('/api/auth/login', data)
}

export const profile = () => request.get('/api/auth/profile')
