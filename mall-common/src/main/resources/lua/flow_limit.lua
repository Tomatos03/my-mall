-- 限流算法: 固定窗口计数限流
local key = KEYS[1]
local max_cnt = tonumber(ARGV[1]) -- 最大操作次数
local time = tonumber(ARGV[2]) -- 等待藏时间

local cur_cnt = redis.call('get', key); -- 当前操作次数
if not cur_cnt then
    cur_cnt = 0 -- redis中不存在key时
else
    cur_cnt = tonumber(cur_cnt)
end

if cur_cnt >= max_cnt then
    return 1 -- 被限流
end

cur_cnt = redis.call('incr', key)
if cur_cnt == 1 then
    redis.call('expire', key, time)
end

return 0 -- 没有被限流