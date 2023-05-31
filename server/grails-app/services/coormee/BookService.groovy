package coormee

import grails.gorm.services.Service

@Service(Book)
abstract class BookService {

    abstract Book get(Serializable id)

    abstract List<Book> list(Map args)

    abstract Long count()

    abstract void delete(Serializable id)

    abstract Book save(Book book)

    List<Book> favourites(Map args){
        return Book.findAllByFavourite(true);
    }

}