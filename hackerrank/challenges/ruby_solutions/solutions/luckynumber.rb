@MAX_LENGTH = 18
@MAX_SUM = 18 * 9
@MAX_SQUARE = 18 * 9 * 9

@primes = Array.new(@MAX_SQUARE + 1,true)

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


@dyn_table = Array.new(@MAX_LENGTH+1, Array.new(@MAX_SUM+1, Array.new(@MAX_SQUARE+1,0)))


def is_lucky(number)
  sumDigit = 0
  sumDigitSquare = 0
  while (number > 0)
    d = number % 10
    sumDigit += d
    sumDigitSquare += d*d
    number = number / 10
  end
  @primes[sumDigit] == true && @primes[sumDigitSquare] == true
end

def fillPrime
  @primes[0] = @primes[1] = false;
  i = 2
  while (i*i <= @MAX_SQUARE)
    if(@primes[i])
      j = 2
      while(j*i <= @MAX_SQUARE)
        @primes[j*i] = false
        j += 1
      end
    end
    i += 1
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
    @primes.each_with_index do |pr, idx2|
      if pr == true
        @primes.each_with_index do |pr2, idx3|
          if idx3 <= idx2
            next
          end
          if pr2 == true
            (0..d-1).each do |i|
              cnt += @dyn_table[digits.length - idx - 1][idx2-sum-i][idx3-sumSquare-i*i] if @dyn_table[digits.length - idx - 1] &&
                  @dyn_table[digits.length - idx - 1][idx2-sum-i] &&
                  @dyn_table[digits.length - idx - 1][idx2-sum-i][idx3-sumSquare-i*i]
            end
          end
        end
      end
    end
    sum += d
    sumSquare += d*d
  end
  cnt
end

fillPrime()

fillDynTable()

t = gets.strip.to_i
t.times do
  input = STDIN.gets.chomp.split(' ')
  puts (calc(input[1].to_i) - calc(input[0].to_i))
end
