import request from '@/utils/request'

export const fetchFlows = (page = 0, size = 10) => request.get('/api/archive-flows', { params: { page, size } })
