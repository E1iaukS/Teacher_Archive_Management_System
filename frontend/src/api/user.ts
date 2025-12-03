import request from '@/utils/request'

export const fetchUsers = (page = 0, size = 10) => request.get('/api/users', { params: { page, size } })
export const createUser = (data: any) => request.post('/api/users', data)
