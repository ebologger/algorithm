@MAX_LENGTH = 18
@MAX_SUM = @MAX_LENGTH * 9
@MAX_SQUARE = @MAX_LENGTH * 9 * 9

@primes_array = Array.new(@MAX_SQUARE + 1,true)
@primes = {}

@dyn_table = []
(@MAX_LENGTH+1).times do
  tmp = []
  (@MAX_SUM+1).times do
    tmp2 = []
    (@MAX_SQUARE+1).times do
      tmp2 << 0
    end
    tmp << tmp2
  end
  @dyn_table << tmp
end


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
  (0..@MAX_LENGTH-1).each do |i|
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
  digits = []
  while (number > 0)
    d = number % 10
    digits << d
    number = number / 10
  end

  digits.reverse!
end

def calc(max)
  cnt = 0
  digits = split(max)
  sum = 0;
  sumSquare = 0;
  digits.each_with_index do |d, idx|
    if idx == digits.length - 1
      (0..d).each do |i|
        cnt += 1 if @primes[i + sum] == true && @primes[i*i + sumSquare] == true
      end
    else
      @primes.each_key do |pr|
        @primes.each_key do |pr2|
          if pr2 < pr
            next
          end
          (0..d-1).each do |i|
            cnt += @dyn_table[digits.length - idx - 1][pr-sum-i][pr2-sumSquare-i*i] if @dyn_table[digits.length - idx - 1] &&
                @dyn_table[digits.length - idx - 1][pr-sum-i] &&
                @dyn_table[digits.length - idx - 1][pr-sum-i][pr2-sumSquare-i*i]
          end
        end

      end
      sum += d
      sumSquare += d*d
    end

  end
  cnt
end

start = Time.now
fillPrime()
puts "Duration: #{Time.now - start} seconds"

start = Time.now
fillDynTable()
puts "Duration: #{Time.now - start} seconds"


t = gets.strip.to_i
t.times do
  input = STDIN.gets.chomp.split(' ')
  puts (calc(input[1].to_i) - calc(input[0].to_i-1))
end
