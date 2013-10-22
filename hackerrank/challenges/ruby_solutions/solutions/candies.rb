# https://www.hackerrank.com/challenges/candies

n = gets.strip.to_i
input = {}
(0..n-1).each do |i|
  input[i] = gets.strip.to_i
end

input = Hash[input.sort {|a,b| a[1]<=>b[1]}]

output = Array.new(n, 1)

input.each do |k,v|
  [-1,1].each do |diff|
    if input[k+diff]
      if input [k+diff] < v && output[k+diff] >= output[k]
        output[k] = output[k+diff] + 1
      end
      if input [k+diff] > v && output[k+diff] <= output[k]
        output[k+diff] = output[k] + 1
      end
    end
  end
end

puts output.inject(:+)