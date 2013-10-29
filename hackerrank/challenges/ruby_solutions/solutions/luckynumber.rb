@MAX_LENGTH = 18
@MAX_SUM = @MAX_LENGTH * 9
@MAX_SQUARE = @MAX_LENGTH * 9 * 9

@primes_array = Array.new(@MAX_SQUARE + 1,true)
@primes = {}

@dyn_table = Array.new(@MAX_LENGTH+1) { Array.new(@MAX_SUM+1) { Array.new(@MAX_SQUARE+1,0) } }

def fillPrime
  @primes_array[0] = @primes_array[1] = false;
  i = 2
  while (i*i <= @MAX_SQUARE)
    if(@primes_array[i])
      j = 2
      while(j*i <= @MAX_SQUARE)
        @primes_array[j*i] = false
        j += 1
      end
    end
    i += 1
  end

  (0..@MAX_SQUARE + 1).each do |i|
    if @primes_array[i] == true
      @primes[i] = true;
    end
  end
end

def fillDynTable
  @dyn_table[0][0][0] = 1
  (0..@MAX_LENGTH-2).each do |i|
    (0..i*9).each do |j|
      (0..i*9*9).each do |k|
        (0..9).each do |l|
          @dyn_table[i+1][j+l][k+l*l] += @dyn_table[i][j][k]
        end
      end
    end
  end
end

def split(number)

  if number == 0
    return [0]
  end

  digits = []
  while (number > 0)
    d = number % 10
    digits << d
    number = number / 10
  end
  digits

end

def calc(max)
  cnt = 0
  digits = split(max)
  digits.reverse!

  @primes.each_key do |pr|
    @primes.each_key do |pr2|
      if pr2 < pr
        next
      end
      sum = 0;
      sumSquare = 0;

      (0..digits.length-2).each do |idx|
        d = digits[idx]
        (0..d-1).each do |i|
          cnt += @dyn_table[digits.length - idx - 1][pr-sum-i][pr2-sumSquare-i*i] if @dyn_table[digits.length - idx - 1] &&
              @dyn_table[digits.length - idx - 1][pr-sum-i] &&
              @dyn_table[digits.length - idx - 1][pr-sum-i][pr2-sumSquare-i*i]
        end
        sum += d
        sumSquare += d*d
      end
    end
  end

  sum = 0;
  sumSquare = 0;
  (0..digits.length-2).each do |idx|
    d = digits[idx]
    sum += d
    sumSquare += d*d
  end

  d = digits[digits.length-1]
  (0..d).each do |i|
    cnt += 1 if @primes[i + sum] == true && @primes[i*i + sumSquare] == true
  end

  cnt
end

def calc2(max)
  result = 0
  digits = split(max)

  sum = 0
  sq_sum = 0
  (digits.length-1).downto(0).each do |i|
    step_result = 0
    (0..digits[i]-1).each do |l|
      (0..9*i).each do |j|
        (0..9*9*i).each do |k|
          if(@primes[j+l+sum] && @primes[k+l*l + sq_sum])
            step_result += @dyn_table[i][j][k]
          end
        end
      end
    end
    result += step_result
    sum += digits[i]
    sq_sum += digits[i]*digits[i]
  end

  if (@primes[sum] && @primes[sq_sum])
    result += 1
  end

  result
end

fillPrime()

fillDynTable()

text=File.open('C:\projects\algorithm\hackerrank\challenges\ruby_solutions\input2.txt').read
text.gsub!(/\r\n?/, "\n")
text.each_line do |line|
  input = line.split(' ')
  input[1] = "999999999999999999" if input[1] == "1000000000000000000"
  puts (calc(input[1].to_i) - calc(input[0].to_i-1))
end

#t = gets.strip.to_i
#t.times do
#  input = STDIN.gets.chomp.split(' ')
#  puts (calc2(input[1].to_i) - calc2(input[0].to_i-1))
#end