T = gets.strip.to_i
T.times do
  input = STDIN.gets.chomp.split(' ')
  N = input[0].to_i
  M = input[1].to_i
  S = input[2].to_i

  input = []

  N.times do
    input << STDIN.gets.chomp.split(' ').map do |i|
      if i == 'L'
        1
      elsif i == 'P'
        -1
      else
        0
      end
    end
  end

  tmp = input.dup

  sum = 0

  M.times do

    (0..N-1).each do |x|
      (0..N-1).each do |y|
        (0..S).each do |i|
          (0..S-i).each do |j|
            if (i != 0 && j != 0)
              if (input[x+i] && input[x+i][y+j] && input[x+i][y+j] != -1)
                tmp[x][y] += input[x+i][y+j]
                sum += input[x+i][y+j]
              end
            end
          end
        end
      end
    end

    input = tmp.dup
  end

  puts sum

end