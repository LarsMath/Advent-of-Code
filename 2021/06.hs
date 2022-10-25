import Utils

count9 :: [Int] -> [Int]
count9 xs = [length (filter (n==) xs) | n <- [0..8]]

grow :: [Int] -> [Int]
grow (x:xs) = (take 6 xs) ++ [(xs !! 6 + x), xs !! 7, x]

solveA :: IO ()
solveA = solve "input/06.txt" (count9 . seperatedInts) (sum . (\(xs) -> (iterate grow xs) !! 80))

solveB :: IO ()
solveB = solve "input/06.txt" (count9 . seperatedInts) (sum . (\(xs) -> (iterate grow xs) !! 256))