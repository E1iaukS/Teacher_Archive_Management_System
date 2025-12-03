import request from '@/utils/request'

export const fetchArchives = (page = 0, size = 10) => request.get('/api/archives', { params: { page, size } })
