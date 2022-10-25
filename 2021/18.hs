import Utils
import Data.List
import Data.Char

type Expr = [(Int, Int)]

level :: Int
level = 5

reduce :: Expr -> Expr
reduce xs
    | any (\(_,b) -> b == level) xs = reduce (explode xs)
    | any (\(a,_) -> a > 9) xs      = reduce (split xs)
    | otherwise = xs

explode :: Expr -> Expr
explode [] = []
explode ((x,5):((a,5):((c,d):xs))) = ((0,level-1):((c+a, d):xs))
explode ((x,y):((a,5):((c,5):((z,w):xs)))) = ((x+a,y):((0,level-1):((z+c,w):xs)))
explode (x:xs) = x : explode xs

split :: Expr -> Expr
split [] = []
split ((x,y):xs)
    | x > 9 = ((x `div` 2, y + 1):(((x+1) `div` 2, y + 1):xs))
    | otherwise = (x,y) : split xs

sum' :: Expr -> Expr -> Expr
sum' a b = reduce (map inc a ++ map inc b)
            where inc (a,b) = (a,b+1)

parse :: String -> Expr
parse xs = parselevel 0 xs
            where   parselevel n (x:xs)
                        | xs == [] = []
                        | x == '[' = parselevel (n+1) xs
                        | x == ']' = parselevel (n-1) xs
                        | x == ',' = parselevel n xs
                        | isDigit x = (digitToInt x, n) : parselevel n xs
                        | otherwise = parselevel n xs

---magnitude :: Expr -> Int


solveA :: IO ()
solveA = solve "input/18test.txt" (map parse . lines) ((\xs -> foldl sum' (head xs) (tail xs)))
