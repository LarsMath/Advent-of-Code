import Utils
import Data.Char

data Operation = Forward Int | Up Int | Down Int
                    deriving Read

parse :: String -> Operation
parse (x:xs) = read ((toUpper x):xs)


operationA :: Operation -> (Int, Int) -> (Int, Int)
operationA (Forward n) (h,v)      = (h + n, v)
operationA (Up n) (h,v)           = (h, v - n)
operationA (Down n) (h,v)         = (h, v + n)

solveA :: IO()
solveA = solve "input/02.txt" (map parse . lines) ((\(a,b) -> a * b) . foldr operationA (0,0))


operationB :: Operation -> (Int, Int, Int) -> (Int, Int, Int)
operationB (Forward n) (h,v,a)    = (h + n, v + a * n, a)
operationB (Up n) (h,v,a)         = (h, v, a - n)
operationB (Down n) (h,v,a)       = (h, v, a + n)

solveB :: IO()
solveB = solve "input/02.txt" (map parse . lines) ((\(a,b,c) -> a * b) . foldr operationB (0,0,0))