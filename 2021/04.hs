import Utils
import Data.List

type Card = [[Int]]
type Perm = [Int]

parseCards :: String -> [Card]
parseCards xs = splitCards . map (map read) . map words . lines $ xs
                where   splitCards [] = []
                        splitCards xss = (take 5 xss) : splitCards (drop 6 xss)

findIndex' :: Int -> Perm -> Int
findIndex' n [] = 1
findIndex' n (x:xs)
    | n == x    = 0
    | otherwise = 1 + findIndex' n xs

invPerm :: Perm -> Perm
invPerm xs = [findIndex' n xs | n <- [0..(length xs - 1)]]

applyPerm :: Perm -> Int -> Int
applyPerm xs y = xs !! y

findTurn :: Perm -> Card -> (Card, Int)
findTurn order card = (card, minimum $ (map maximum invCard) ++ (map maximum . transpose $ invCard))
                        where invCard = map (map (applyPerm (invPerm (order)))) card

sumValuesAfterTurn :: Perm -> Int -> Card -> Int
sumValuesAfterTurn order turn card = sum ((concat card) \\ (take (turn + 1) order))

solveA :: IO ()
solveA = do order <- readFile "input/04order.txt"
            cards <- readFile "input/04.txt"
            let parsedCards = parseCards cards
            let parsedOrder = seperatedInts order
            let (firstCard, turn) = maximumOn (negate . snd) . map (findTurn parsedOrder) $ parsedCards
            print ((parsedOrder !! (turn)) * sumValuesAfterTurn parsedOrder turn firstCard)

solveB :: IO ()
solveB = do order <- readFile "input/04order.txt"
            cards <- readFile "input/04.txt"
            let parsedCards = parseCards cards
            let parsedOrder = seperatedInts order
            let (lastCard, turn) = maximumOn snd . map (findTurn parsedOrder) $ parsedCards
            print ((parsedOrder !! (turn)) * sumValuesAfterTurn parsedOrder turn lastCard)