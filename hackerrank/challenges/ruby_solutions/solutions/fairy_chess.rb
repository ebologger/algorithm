MODULO = 1000000007

time1 = 0
time2 = 0
time3 = 0
  def deep_copy(array)
    aaa = Time.now
    return Marshal.load( Marshal.dump(array) )
    time2 += Time.now - aaa
  end

File.open('C:\projects\algorithm\hackerrank\challenges\ruby_solutions\input.txt', 'r') do |f1|
  t = f1.gets.strip.to_i
  t.times do
    aaa = Time.now
    input = f1.gets.chomp.split(' ')
    n = input[0].to_i
    m = input[1].to_i
    s = input[2].to_i

    input = []

    n.times do
      input << f1.gets.chomp.split(//).map do |i|
        if i == 'L'
          1
        elsif i == 'P'
          -1
        else
          0
        end
      end
    end
    time3 += Time.now - aaa
    puts time3
    tmp = deep_copy(input)

    m.times do
      a = 1
      (0..n-1).each do |x|
        a = 1
        (0..n-1).each do |y|
          a = 1
          (0..s).each do |i|
            a = 1
            (1..s-i).each do |j|
              a = 1
              if (x.between?(0, n-1) && y.between?(0, n-1) && input[x][y] != -1)
                if ((x+i).between?(0, n-1) && (y+j).between?(0, n-1) && input[x+i][y+j] != -1)
                  tmp[x][y] = (tmp[x][y] + input[x+i][y+j]) % MODULO
                end
                if ((x-j).between?(0, n-1) && (y+i).between?(0, n-1) && input[x-j][y+i] != -1)
                  tmp[x][y] = (tmp[x][y] + input[x-j][y+i]) % MODULO
                end
                if ((x-i).between?(0, n-1) && (y-j).between?(0, n-1) && input[x-i][y-j] != -1)
                  tmp[x][y] = (tmp[x][y] + input[x-i][y-j]) % MODULO
                end
                if ((x+j).between?(0, n-1) && (y-i).between?(0, n-1) && input[x+j][y-i] != -1)
                  tmp[x][y] = (tmp[x][y] + input[x+j][y-i]) % MODULO
                end
              end
            end
          end
        end
      end
      input = deep_copy(tmp)
    end

    sum = 0

    input.each do |a|
      a.each do |b|
        if b != -1
          sum = (sum + b) % MODULO
        end
      end
    end
    time1 += Time.now - aaa
    puts sum

  end
end

puts time1
puts time2
