import request from '@/utils/request'

export const fetchTeachers = (page = 0, size = 10) => request.get('/api/teachers', { params: { page, size } })
export const createTeacher = (data: any) => request.post('/api/teachers', data)
