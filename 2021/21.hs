import Utils

p1Score :: Int -> [Int]
p1Score s = takeWhile (<1000) $ cumsum [((3*(3*n*n + 5*n) + 6 + s - 1) `mod` 10) + 1 | n <- [0..]]

p2Score :: Int -> [Int]
p2Score s = takeWhile (<1000) $ cumsum [((3*(3*n*n + 8*n) + 15 + s - 1) `mod` 10) + 1 | n <- [0..]]

cumsum :: [Int] -> [Int]
cumsum [] = []
cumsum (x:xs) = x : [x + y | y <- cumsum xs]

solution :: Int
solution
    | length p1 <= length p2    = 3*(2*n + 1) *  (p2 !! (n-1))
    | otherwise                 = 6*(n+1) * (p1 !! n)

    where   p1 = p1Score 10
            p2 = p2Score 4
            n = min (length p1) (length p2)

diracDice :: 