; furkan ozdemir
; 2018400201
; compiling: yes
; complete: yes
#lang racket

;; given
(struct num (value grad)
    #:property prop:custom-write
    (lambda (num port write?)
        (fprintf port (if write? "(num ~s ~s)" "(num ~a ~a)")
            (num-value num) (num-grad num))))

(define list-to-num (lambda (n) (if (list? n) (num (cadr n) (caddr n)) n)))

(define get-value (lambda (num-list) (
    cond [(not (list? num-list)) (num-value num-list)]
         [(eq? num-list '()) '()]
         [else (cons (num-value (list-to-num (car num-list))) (get-value (cdr num-list)))]
                                      )))

(define get-grad (lambda (num-list) (
    cond [(not (list? num-list)) (num-grad num-list)]
         [(eq? num-list '()) '()]
         [else (cons (num-grad (list-to-num (car num-list))) (get-grad (cdr num-list)))]
                                     )))

(define add (lambda args (num (foldl + 0 (get-value args)) (foldl + 0 (get-grad args)))))

(define mul-list (lambda (list1 list2) (
    cond [(eq? list1 '()) '()]
         [else (cons (foldl * (car list1) list2) (mul-list (cdr list1) list2))]
                                        )))

(define mul (lambda args (num (foldl * 1 (get-value args))
                              (foldl + 0 (map / (mul-list (get-grad args) (get-value args)) (get-value args)))
                              )))                                   
                                     
(define sub (lambda (num1 num2) (num (- (num-value num1) (num-value num2)) (- (num-grad num1) (num-grad num2)))))

(define relu (lambda (x) (if (> (num-value x) 0) x (num 0.0 0.0))))

(define mse (lambda (x y) (mul (sub x y) (sub x y))))

(define create-hash-values (lambda (names values var) (
     cond [(eq? names '()) '()]
          [else (cons (num (car values) (if (eq? (car names) var) 1.0 0.0)) (create-hash-values (cdr names) (cdr values) var))]
                                                       )))

(define create-hash-list (lambda (list1 list2) (
     cond [(eq? list1 '()) '()]
          [else (cons (car list1) (cons (car list2) (create-hash-list (cdr list1) (cdr list2))))]
                                                )))

(define create-hash (lambda (names values var) (
     apply hash (create-hash-list names (create-hash-values names values var)) 
                                                )))

(define parse (lambda (hash expr) (
     cond [(eq? expr '()) '()]
          [(list? expr) (cons (parse hash (car expr)) (parse hash (cdr expr)))]
          [(eq? expr '+) 'add]
          [(eq? expr '*) 'mul]
          [(eq? expr '-) 'sub]
          [(eq? expr 'mse) 'mse]
          [(eq? expr 'relu) 'relu]
          [(number? expr) (num expr 0.0)]
          [else (hash-ref hash expr)]
                                   )))

(define grad (lambda (names values var expr) (
      num-grad (eval (parse (create-hash names values var) expr))
                                              )))

(define partial-grad-calc (lambda (names1 names2 values vars expr) (
      cond [(eq? names1 '()) '()]
           [else (cons (if (member (car names1) vars) (grad names2 values (car vars) expr) 0.0) (partial-grad-calc (cdr names1) names2 values (if (member (car names1) vars) (cdr vars) vars) expr))]
                                                       )))


(define partial-grad (lambda (names values vars expr) (
      partial-grad-calc names names values vars expr
                                                       )))

(define gradient-descent (lambda (names values vars lr expr) 
      (map - values (map(lambda (number) (* lr number)) (partial-grad names values vars expr))) 
                                                                  ))
      
(define optimize (lambda (names values vars lr k expr) (
      cond[(eq? k 1) (gradient-descent names values vars lr expr)]
          [else (optimize names (gradient-descent names values vars lr expr) vars lr (- k 1) expr)]
                                                        )))

