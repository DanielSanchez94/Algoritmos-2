import Data.Maybe

-- |Data type representing a Deterministic Finite Automaton, parametrized by a type of states.
type DFA st = ([st], [Char], st->Char->st, st, st->Bool)

-- Genera sublistas de una lista
sublist :: Eq a => [a] -> [[a]]
sublist [] = [[]]
sublist (x:xs) = [(x:ys) | ys<-sublist xs] ++ sublist xs

-- De una lista de deltas elimina las que no son funcionales y totales
repOkList :: [Int] -> [Char] -> [[((Int,Char),Int)]] -> [[((Int,Char),Int)]]
repOkList ys zs [x] | functional x && total [(s,c) | s<-ys, c<-zs] x = [x]
                    | otherwise = []
repOkList ys zs (x:xs)  | functional x && total [(s,c) | s<-ys, c<-zs] x = x : repOkList ys zs xs
                        | otherwise = repOkList ys zs xs

-- Verifica si una lista delta es funcional
functional :: [((Int,Char),Int)] -> Bool
functional [] = True
functional [x] = True
functional (x:y:xs)  |fst x == fst y && snd x /= snd y = False
                |fst x == fst y && snd x == snd y = False
                |otherwise = True && functional (y:xs)

-- Verifica si una lista delta es total
total ::[(Int,Char)] -> [((Int,Char),Int)] -> Bool
total [] _ = True
total (x:xs) ys = elem x (reduc ys) && total xs ys
              where reduc ts = [fst t | t <- ts]

--Elimina los elementos repetidos de una lista
removeDups :: Eq a => [a] -> [a]
removeDups [] = []
removeDups (x:xs)
          | elem x xs = (removeDups xs)
          | otherwise  = x:(removeDups xs)

-- Devuelve true si la interseccion entre dos listas de palabras es distinta de
intersec :: [String] -> [String] -> Bool
intersec xs [] = False
intersec xs (y:ys) = elem y xs || intersec xs ys

-- Imprime un automata con la delta en formato de lista (Aun no se muestran los estados finales)
printDFA :: DFA Int -> ([Int],[Char],[((Int, Char),Int)],Int,[Int])
printDFA (qs,sigma,delta,s,inF) = (qs,sigma,getDelta qs sigma delta,s, getFinals qs inF)

-- Funcion para recuperar la lista delta desde funFromTuples
getDelta :: [Int] -> [Char] -> (Int->Char->Int) -> [((Int,Char),Int)]
getDelta qs sigma delta = [((q,c),delta q c) | q<-qs, c<-sigma ]

--Funcion para recuperar el conjunto de estados finales
getFinals :: [Int] -> (Int -> Bool) -> [Int]
getFinals qs inF = [q | q<-qs, inF q]

-- Evalua si un automata acepta una cadena o no
evalDFA :: DFA st -> String -> Bool
evalDFA (qs, sigma, delta, s, inF) w =
  inF (deltaStar s w)
  where deltaStar q [] = q
        deltaStar q (a:w) = deltaStar (delta q a) w

--Crea una funcion a partir de una lista de tuplas
funFromTuples :: Eq a =>[(a,b)] -> a -> b
funFromTuples ts x = fromJust (lookup x ts)

-- Evalua si un DFA acepta un grupo de palabras y no acepta otro grupo
evalString :: DFA Int -> [String] -> [String] ->Bool
evalString dfa [] [] = True
evalString dfa (x:xs) [] = evalDFA dfa x && evalString dfa xs []
evalString dfa [] (y:ys) = not (evalDFA dfa y) && evalString dfa [] ys
evalString dfa (x:xs) (y:ys) = (evalDFA dfa x) && (not (evalDFA dfa y)) && evalString dfa xs ys

-- Genera una lista de automatas variando el delta y el conjunto de estados finales
genDFA :: [Int] -> [Char] -> [[((Int,Char),Int)]] -> [DFA Int]
genDFA xs ys zs = [(xs, ys, (\s c -> funFromTuples d (s,c)), 0, (\s -> elem s z)) | z<-sublist xs, d<-zs]

-- Genera la lista de todos los posibles deltas a partir de un conjunto de
-- estados y un alfabeto
generateDelta :: [Int] -> [Char] -> [[((Int,Char),Int)]]
generateDelta xs ys = removeDups (repOkList xs ys (init (sublist [((x,y),z) | x <- xs, y <- ys, z <- xs])))

-- Genera automatas a partir de 1 estado a k estados y se queda con el primero
-- que acepte todas las palabras en  pos y rechase  todas las palabras en neg
auxFunction :: Int -> Int -> [Char] -> [DFA Int] -> [String] -> [String] -> DFA Int
auxFunction k n alfab [] pos neg | n<=k = auxFunction k (n+1) alfab (genDFA [x | x <- [0..n-1]] alfab (generateDelta [x | x <- [0..n-1]] alfab)) pos neg
                                 | otherwise = error "No es posible formar un automata"
auxFunction k n alfab (x:xs) pos neg | evalString x pos neg = x
                                     | otherwise = auxFunction k n alfab xs pos neg

-- Funcion principal, utilizada para inicializar a la funcion auxFunction
mainFunction :: Int -> [Char] -> [String] -> [String] -> ([Int],[Char],[((Int, Char),Int)],Int,[Int])
mainFunction 0 _ _ _ = error "No se puede formar DFA con 0 estados"
mainFunction k alfab pos neg | intersec pos neg = error "No es posible encontrar automata que reconozca y no reconozca una palabra"
                             | otherwise = printDFA (auxFunction k 2 alfab (genDFA [0] alfab (generateDelta [0] alfab)) pos neg)
