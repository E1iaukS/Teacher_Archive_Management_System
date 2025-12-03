import request from '@/utils/request'

export const fetchResignations = (page = 0, size = 10) => request.get('/api/resignations', { params: { page, size } })
