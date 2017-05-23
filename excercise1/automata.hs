import Data.Maybe

-- |Data type representing a Deterministic Finite Automaton, parametrized by a type of states.
type DFA st = ([st], [Char], st->Char->st, st, st->Bool)

-- | Function f_M: Sigma* -> {True,False} defined by DFA M
-- Uses auxiliary function delta*: Q x Sigma* -> Q
evalDFA :: DFA st -> String -> Bool
evalDFA (qs, sigma, delta, s, inF) w =
  inF (deltaStar s w)
  where deltaStar q [] = q
        deltaStar q (a:w) = deltaStar (delta q a) w

--Crea una funcion a partir de una lista de tuplas
funFromTuples :: Eq a =>[(a,b)] -> a -> b
funFromTuples ts x = fromJust (lookup x ts)

-- Genera sublistas de una lista
sublist :: Eq a => [a] -> [[a]]
sublist [] = [[]]
sublist (x:xs) = [(x:ys) | ys<-sublist xs] ++ sublist xs

-- De una lista de listas elimina las listas que no son funcionales y totales
repOkList :: Eq a => Eq b => [[(a,b)]] -> [[(a,b)]]
repOkList [[]] = [[]]
repOkList (x:xs)  | repOk x = x : repOkList xs
                  | otherwise = repOkList xs
                  where repOk [] = True
                        repOk [x] = True
                        repOk (x:y:xs)|fst x == fst y && snd x /= snd y = False
                                      |fst x == fst y && snd x == snd y = False
                                      |otherwise = True && repOk (y:xs)

--Elimina los elementos repetido de una lista
removeDups :: Eq a => [a] -> [a]
removeDups [] = []
removeDups (x:xs)
          | elem x xs = (removeDups xs)
          | otherwise  = x:(removeDups xs)

-- Evalua si un DFA hacepta un grupo de palabras y no acepta otro grupo
evalString :: DFA Int -> [String] -> [String] ->Bool
evalString dfa [] [] = True
evalString dfa [] [y] = not (evalDFA dfa y)
evalString dfa [x] [] = evalDFA dfa x
evalString dfa (x:xs) (y:ys) = (evalDFA dfa x) && (not (evalDFA dfa y)) && evalString dfa xs ys

genDFA :: [Int] -> [Char] -> [[((Int,Char),Int)]] -> [DFA Int]
genDFA xs ys zs = [(xs, ys, (\s c -> funFromTuples d (s,c)), 0, (\s -> elem s z)) | z<-sublist xs, d<-zs]

-- Genera todos los posibles estados de la funcion de transicion delta
generateDelta :: [Int] -> [Char] -> [[((Int,Char),Int)]]
generateDelta xs ys = removeDups (repOkList (sublist [((x,y),z) | x <- xs, y <- ys, z <- xs]))

generateEst :: Int -> [Int]
generateEst n = [x | x <- [0..n-1]]

auxFunction :: Int -> Int -> [Char] -> [DFA Int] -> [String] -> [String] -> DFA Int
auxFunction k n alfab [] pos neg | n<k = auxFunction k (n+1) alfab (genDFA [x | x <- [0..n-1]] alfab (generateDelta [x | x <- [0..n-1]] alfab)) pos neg
                                 | otherwise = error "No es posible formar un automata"
auxFunction k n alfab (x:xs) pos neg | evalString x pos neg = x
                                     | otherwise = auxFunction k n alfab xs pos neg

mainFunction :: Int -> [Char] -> [String] -> [String] -> DFA Int
mainFunction k alfab pos neg = auxFunction k 1 alfab (genDFA [0] alfab (generateDelta [0] alfab)) pos neg






--
