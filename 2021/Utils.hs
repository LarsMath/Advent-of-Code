module Utils where
import Data.Char
import Data.List

readDigitLines :: String -> [[Int]]
readDigitLines = map (map (read . pure)) . words

readInts :: String -> [Int]
readInts = map read . words

seperatedInts :: String -> [Int]
seperatedInts = map read . words . replace
                where   replace [] = []
                    	replace (x:xs)
                        	| isDigit x	= x : (replace xs)
                        	| otherwise = ' ' : (replace xs)

solve :: (Show b) => String -> (String -> a) -> (a -> b) -> IO()
solve file parse compute = do   contents <- readFile file
                                print . compute . parse $ contents

maximumOn :: (a -> Int) -> [a] -> a
maximumOn f [] = error "maximumOn: empty list"
maximumOn f (x:xs) = g x (f x) xs
	where
		g v mv [] = v
		g v mv (x:xs) 	| mx > mv = g x mx xs
						| otherwise = g v mv xs
			where mx = f x

combinations :: Eq a => [a] -> [(a,a)]
combinations xs = nubBy (\(a,b) -> \(c,d) -> (a == c && b == d) || (a == d) && (b == d)) [(x,y) | x <- xs, y <- xs]

distinctCombinations :: Eq a => [a] -> [(a,a)]
distinctCombinations = filter (\(a,b) -> a /= b) . combinations
