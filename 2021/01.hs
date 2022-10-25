import Utils

compute :: Int -> [Int] -> Int
compute n xs = sum . map fromEnum $ zipWith (<) xs $ (iterate tail xs) !! n

solveA :: IO ()
solveA = solve "input/01.txt" readInts (compute 1)

solveB :: IO ()
solveB = solve "input/01.txt" readInts (compute 3)
